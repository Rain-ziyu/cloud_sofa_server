package asia.huayu.service.impl;

import asia.huayu.common.entity.Result;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.common.util.IdUtils;
import asia.huayu.common.util.IpUtil;
import asia.huayu.common.util.RequestUtil;
import asia.huayu.constant.RedisConstant;
import asia.huayu.entity.Article;
import asia.huayu.entity.ArticleTag;
import asia.huayu.entity.TempArticle;
import asia.huayu.entity.User;
import asia.huayu.mapper.ArticleMapper;
import asia.huayu.mapper.ArticleTagMapper;
import asia.huayu.mapper.CategoryMapper;
import asia.huayu.model.dto.*;
import asia.huayu.model.vo.ArticlePasswordVO;
import asia.huayu.model.vo.ArticleVO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.*;
import asia.huayu.service.feign.ArticleFeignService;
import asia.huayu.strategy.context.SearchStrategyContext;
import asia.huayu.util.BeanCopyUtil;
import asia.huayu.util.PageUtil;
import asia.huayu.util.UserUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static asia.huayu.enums.StatusCodeEnum.ARTICLE_ACCESS_FAIL;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private CategoryMapper categoryMapper;


    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ArticleFeignService articleFeignService;
    @Autowired
    private TempArticleService tempArticleService;
    @Autowired
    private SearchStrategyContext searchStrategyContext;

    @SneakyThrows
    @Override
    public TopAndFeaturedArticlesDTO listTopAndFeaturedArticles() {
        List<ArticleCardDTO> articleCardDTOs = articleMapper.listTopAndFeaturedArticles();
        if (articleCardDTOs.size() == 0) {
            return new TopAndFeaturedArticlesDTO();
        } else if (articleCardDTOs.size() > 3) {
            articleCardDTOs = articleCardDTOs.subList(0, 3);
        }
        TopAndFeaturedArticlesDTO topAndFeaturedArticlesDTO = new TopAndFeaturedArticlesDTO();
        topAndFeaturedArticlesDTO.setTopArticle(articleCardDTOs.get(0));
        articleCardDTOs.remove(0);
        topAndFeaturedArticlesDTO.setFeaturedArticles(articleCardDTOs);
        return topAndFeaturedArticlesDTO;
    }

    @SneakyThrows
    @Override
    public PageResultDTO<ArticleCardDTO> listArticles() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, 0)
                .eq(Article::getStatus, 1);
        CompletableFuture<Long> asyncCount = CompletableFuture.supplyAsync(() -> articleMapper.selectCount(queryWrapper));
        List<ArticleCardDTO> articles = articleMapper.listArticles(PageUtil.getLimitCurrent(), PageUtil.getSize());
        return new PageResultDTO<>(articles, asyncCount.get());
    }

    @SneakyThrows
    @Override
    public PageResultDTO<ArticleCardDTO> listArticlesByCategoryId(Integer categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>().eq(Article::getCategoryId, categoryId);
        CompletableFuture<Long> asyncCount = CompletableFuture.supplyAsync(() -> articleMapper.selectCount(queryWrapper));
        List<ArticleCardDTO> articles = articleMapper.getArticlesByCategoryId(PageUtil.getLimitCurrent(), PageUtil.getSize(), categoryId);
        return new PageResultDTO<>(articles, asyncCount.get());
    }

    @SneakyThrows
    @Override
    @Transactional
    public ArticleDTO getArticleById(Integer articleId) {
        Article articleForCheck = articleMapper.selectOne(new LambdaQueryWrapper<Article>().eq(Article::getId, articleId));
        if (Objects.isNull(articleForCheck)) {
            return null;
        }
        String name = UserUtil.getAuthentication().getName();
        User user = userService.getUserByUsername(name);
        if (articleForCheck.getStatus().equals(2)) {
            Boolean isAccess;
            try {
                isAccess = redisService.sIsMember(RedisConstant.ARTICLE_ACCESS + user.getId(), articleId);
            } catch (Exception exception) {
                throw new ServiceProcessException(ARTICLE_ACCESS_FAIL.getDesc());
            }
            if (isAccess.equals(false)) {
                throw new ServiceProcessException(ARTICLE_ACCESS_FAIL.getDesc());
            }
        }
        updateArticleViewsCount(articleId);
        // 异步获取文章实体
        CompletableFuture<ArticleDTO> asyncArticle = CompletableFuture.supplyAsync(() -> articleMapper.getArticleById(articleId));
        // 异步获取文章卡片 上一张
        CompletableFuture<ArticleCardDTO> asyncPreArticle = CompletableFuture.supplyAsync(() -> {
            ArticleCardDTO preArticle = articleMapper.getPreArticleById(articleId);
            if (Objects.isNull(preArticle)) {
                preArticle = articleMapper.getLastArticle();
            }
            return preArticle;
        });
        // 异步获取文章卡片 下一张
        CompletableFuture<ArticleCardDTO> asyncNextArticle = CompletableFuture.supplyAsync(() -> {
            ArticleCardDTO nextArticle = articleMapper.getNextArticleById(articleId);
            if (Objects.isNull(nextArticle)) {
                nextArticle = articleMapper.getFirstArticle();
            }
            return nextArticle;
        });
        ArticleDTO article = asyncArticle.get();
        if (Objects.isNull(article)) {
            return null;
        }
        // 文章阅读数
        Double score = redisService.zScore(RedisConstant.ARTICLE_VIEWS_COUNT, articleId);
        if (Objects.nonNull(score)) {
            article.setViewCount(score.intValue());
        }
        article.setPreArticleCard(asyncPreArticle.get());
        article.setNextArticleCard(asyncNextArticle.get());
        return article;
    }

    @Override
    public void accessArticle(ArticlePasswordVO articlePasswordVO) {
        Article article = articleMapper.selectOne(new LambdaQueryWrapper<Article>().eq(Article::getId, articlePasswordVO.getArticleId()));
        if (Objects.isNull(article)) {
            throw new ServiceProcessException("文章不存在");
        }
        User user = userService.getUserByUsername(UserUtil.getAuthentication().getName());
        if (article.getPassword().equals(articlePasswordVO.getArticlePassword())) {
            // 将密码验证记录记录放入redis 查看时确实是否检验过密码
            redisService.sAdd(RedisConstant.ARTICLE_ACCESS + user.getId(), articlePasswordVO.getArticleId());
        } else {
            throw new ServiceProcessException("密码错误");
        }
    }

    @SneakyThrows
    @Override
    public PageResultDTO<ArticleCardDTO> listArticlesByTagId(Integer tagId) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getTagId, tagId);
        CompletableFuture<Long> asyncCount = CompletableFuture.supplyAsync(() -> articleTagMapper.selectCount(queryWrapper));
        List<ArticleCardDTO> articles = articleMapper.listArticlesByTagId(PageUtil.getLimitCurrent(), PageUtil.getSize(), tagId);
        return new PageResultDTO<>(articles, asyncCount.get());
    }

    @SneakyThrows
    @Override
    public PageResultDTO<ArchiveDTO> listArchives() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>().eq(Article::getIsDelete, 0).eq(Article::getStatus, 1);
        CompletableFuture<Long> asyncCount = CompletableFuture.supplyAsync(() -> articleMapper.selectCount(queryWrapper));
        List<ArticleCardDTO> articles = articleMapper.listArchives(PageUtil.getLimitCurrent(), PageUtil.getSize());
        HashMap<String, List<ArticleCardDTO>> map = new HashMap<>();
        for (ArticleCardDTO article : articles) {
            LocalDateTime createTime = article.getCreateTime();
            int month = createTime.getMonth().getValue();
            int year = createTime.getYear();
            String key = year + "-" + month;
            if (Objects.isNull(map.get(key))) {
                List<ArticleCardDTO> articleCardDTOS = new ArrayList<>();
                articleCardDTOS.add(article);
                map.put(key, articleCardDTOS);
            } else {
                map.get(key).add(article);
            }
        }
        List<ArchiveDTO> archiveDTOs = new ArrayList<>();
        map.forEach((key, value) -> archiveDTOs.add(ArchiveDTO.builder().Time(key).articles(value).build()));
        archiveDTOs.sort((o1, o2) -> {
            String[] o1s = o1.getTime().split("-");
            String[] o2s = o2.getTime().split("-");
            int o1Year = Integer.parseInt(o1s[0]);
            int o1Month = Integer.parseInt(o1s[1]);
            int o2Year = Integer.parseInt(o2s[0]);
            int o2Month = Integer.parseInt(o2s[1]);
            if (o1Year > o2Year) {
                return -1;
            } else if (o1Year < o2Year) {
                return 1;
            } else {
                return Integer.compare(o2Month, o1Month);
            }
        });
        return new PageResultDTO<>(archiveDTOs, asyncCount.get());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveOrUpdateArticle(ArticleVO articleVO) {
        Authentication authentication = UserUtil.getAuthentication();
        String token;
        // 如果该用户未认证
        if (ObjectUtil.isNull(authentication) || authentication.getPrincipal().equals("anonymousUser")) {
            //     创建匿名用户进行发布
            token = tokenService.getSystemToken();
            if (ObjectUtil.isNull(articleVO.getId())) {
                //     如果接口没有传递id说明是创建新的
                Result<String> result = articleFeignService.saveOrUpdateArticle(articleVO, token);
                if (!result.isSuccess()) {
                    throw new ServiceProcessException(result.getMessage());
                }
                HttpServletRequest request = RequestUtil.getRequest();
                String ipAddress = IpUtil.getIpAddress(request);
                UserAgent userAgent = IpUtil.getUserAgent(request);
                Browser browser = userAgent.getBrowser();
                OperatingSystem operatingSystem = userAgent.getOperatingSystem();
                String tmpUserEnv = ipAddress + browser.getName() + operatingSystem.getName();
                TempArticle tempArticle = new TempArticle();
                tempArticle.setId(IdUtils.getId());
                tempArticle.setTmpUserEnv(tmpUserEnv);
                tempArticle.setArticleId(Integer.parseInt(result.getData()));
                tempArticleService.save(tempArticle);
                return Result.OK(String.valueOf(tempArticle.getId()));
            } else {
                //     临时用户更新文章时 传递的也是正常的文章id
                Result<String> result = articleFeignService.saveOrUpdateArticle(articleVO, token);
                if (!result.isSuccess()) {
                    throw new ServiceProcessException(result.getMessage());
                }
                result.setData(String.valueOf(articleVO.getId()));
                return result;
            }
        } else {
            // 如果是登录用户直接保存或更新 不是发布人时无权限
            token = authentication.getCredentials().toString();
            Result<String> result = articleFeignService.saveOrUpdateArticle(articleVO, token);
            if (!result.isSuccess()) {
                throw new ServiceProcessException(result.getMessage());
            }
            return result;
        }

    }

    /**
     * 方法listArchivesByUser作用为：
     * 获取该用户发表的文章
     *
     * @return asia.huayu.model.dto.PageResultDTO<asia.huayu.model.dto.ArchiveDTO>
     * @throws
     * @author RainZiYu
     */
    @Override
    public PageResultDTO<ArticleListDTO> listArticlesByUser(ConditionVO conditionVO) {
        Authentication authentication = UserUtil.getAuthentication();
        String token;
        // 如果该用户未认证
        if (ObjectUtil.isNull(authentication) || authentication.getPrincipal().equals("anonymousUser")) {
            //     如果用户未登录 返回空让他从cookie中找
            throw new ServiceProcessException("用户未登录，去local查找");
        } else {
            token = authentication.getCredentials().toString();
        }
        Result<PageResultDTO<ArticleListDTO>> pageResultDTOResult = articleFeignService.getArticlesCurrentUser(conditionVO, token);
        return pageResultDTOResult.getData();
    }


    @Override
    public List<ArticleSearchDTO> listArticlesBySearch(ConditionVO condition) {
        return searchStrategyContext.executeSearchStrategy(condition.getKeywords());
    }

    @Override
    public PageResultDTO listArticlesByTempId(List<Long> tempArticleIds, ConditionVO conditionVO) {
        List<Integer> articleIds = tempArticleService.getArticleIds(tempArticleIds);
        String token = tokenService.getSystemToken();
        ArticleIdAndFilterDTO articleIdAndFilterDTO = new ArticleIdAndFilterDTO();
        articleIdAndFilterDTO.setArticleIds(articleIds);
        articleIdAndFilterDTO.setConditionVO(conditionVO);
        Result<PageResultDTO<ArticleListDTO>> pageResultDTOResult = articleFeignService.listArticlesById(articleIdAndFilterDTO, token);
        // 将使用Feign调用获取到的真实文章信息封装为临时文章
        if (pageResultDTOResult.isSuccess()) {
            List<TempArticleListDTO> tempArticleListDTOS = new ArrayList<>();
            pageResultDTOResult.getData().getRecords().forEach(x -> {
                TempArticle tempArticle = tempArticleService.getOne(new LambdaQueryWrapper<TempArticle>().eq((TempArticle::getArticleId), x.getId()));
                TempArticleListDTO tempArticleListDTO = BeanCopyUtil.copyObject(x, TempArticleListDTO.class);
                tempArticleListDTO.setId(String.valueOf(tempArticle.getId()));
                tempArticleListDTOS.add(tempArticleListDTO);
            });
            return new PageResultDTO<TempArticleListDTO>(tempArticleListDTOS, pageResultDTOResult.getData().getCount());
        } else {
            return new PageResultDTO<ArticleListDTO>(new ArrayList(), 0);
        }

    }

    @Override
    public Result<ArticleViewDTO> getArticleBackById(Long articleId) {
        Authentication authentication = UserUtil.getAuthentication();
        String token;
        // 如果该用户未认证
        if (ObjectUtil.isNull(authentication) || authentication.getPrincipal().equals("anonymousUser")) {
            //     创建匿名用户进行获取编辑信息
            token = tokenService.getSystemToken();
            // 匿名用户传递临时文章的id 需要进行转换
            TempArticle tempArticle = tempArticleService.getById(articleId);
            Result<ArticleViewDTO> articleBackById = articleFeignService.getArticleBackById(tempArticle.getArticleId(), token);
            return articleBackById;
        } else {
            // 如果是登录用户用该用户身份直接获取
            token = authentication.getCredentials().toString();
            Result<ArticleViewDTO> articleBackById = articleFeignService.getArticleBackById(Math.toIntExact(articleId), token);
            return articleBackById;
        }

    }

    public void updateArticleViewsCount(Integer articleId) {
        redisService.zIncr(RedisConstant.ARTICLE_VIEWS_COUNT, articleId, 1D);
    }


}
