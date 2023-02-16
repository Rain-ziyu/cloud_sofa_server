package asia.huayu.service.impl;

import asia.huayu.constant.CommonConstant;
import asia.huayu.constant.RedisConstant;
import asia.huayu.entity.About;
import asia.huayu.entity.Article;
import asia.huayu.entity.Comment;
import asia.huayu.entity.WebsiteConfig;
import asia.huayu.mapper.*;
import asia.huayu.model.dto.*;
import asia.huayu.model.vo.AboutVO;
import asia.huayu.model.vo.WebsiteConfigVO;
import asia.huayu.service.AuroraInfoService;
import asia.huayu.service.RedisService;
import asia.huayu.service.UniqueViewService;
import asia.huayu.util.BeanCopyUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AuroraInfoServiceImpl implements AuroraInfoService {

    @Autowired
    private WebsiteConfigMapper websiteConfigMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TalkMapper talkMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private AboutMapper aboutMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UniqueViewService uniqueViewService;



    @Override
    public void report() {
// TODO: 使用request 判断是否是统一用户访问 不是计数+1
        redisService.incr(RedisConstant.BLOG_VIEWS_COUNT, 1);

    }

    @SneakyThrows
    @Override
    public AuroraHomeInfoDTO getAuroraHomeInfo() {
        CompletableFuture<Long> asyncArticleCount = CompletableFuture.supplyAsync(() -> articleMapper.selectCount(new LambdaQueryWrapper<Article>().eq(Article::getIsDelete, CommonConstant.FALSE)));
        CompletableFuture<Long> asyncCategoryCount = CompletableFuture.supplyAsync(() -> categoryMapper.selectCount(null));
        CompletableFuture<Long> asyncTagCount = CompletableFuture.supplyAsync(() -> tagMapper.selectCount(null));
        CompletableFuture<Long> asyncTalkCount = CompletableFuture.supplyAsync(() -> talkMapper.selectCount(null));
        CompletableFuture<WebsiteConfigDTO> asyncWebsiteConfig = CompletableFuture.supplyAsync(this::getWebsiteConfig);
        CompletableFuture<Long> asyncViewCount = CompletableFuture.supplyAsync(() -> {
            Object count = redisService.get(RedisConstant.BLOG_VIEWS_COUNT);
            return Long.parseLong(Optional.ofNullable(count).orElse(0).toString());
        });
        return AuroraHomeInfoDTO.builder()
                .articleCount(Math.toIntExact(asyncArticleCount.get()))
                .categoryCount(Math.toIntExact(asyncCategoryCount.get()))
                .tagCount(Math.toIntExact(asyncTagCount.get()))
                .talkCount(Math.toIntExact(asyncTalkCount.get()))
                .websiteConfigDTO(asyncWebsiteConfig.get())
                .viewCount(Math.toIntExact(asyncViewCount.get())).build();
    }

    @Override
    public AuroraAdminInfoDTO getAuroraAdminInfo() {
        Object count = redisService.get(RedisConstant.BLOG_VIEWS_COUNT);
        Integer viewsCount = Integer.parseInt(Optional.ofNullable(count).orElse(0).toString());
        Integer messageCount = Math.toIntExact(commentMapper.selectCount(new LambdaQueryWrapper<Comment>().eq(Comment::getType, 2)));
        Integer userCount = Math.toIntExact(userInfoMapper.selectCount(null));
        Integer articleCount = Math.toIntExact(articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, CommonConstant.FALSE)));
        List<UniqueViewDTO> uniqueViews = uniqueViewService.listUniqueViews();
        List<ArticleStatisticsDTO> articleStatisticsDTOs = articleMapper.listArticleStatistics();
        List<CategoryDTO> categoryDTOs = categoryMapper.listCategories();
        List<TagDTO> tagDTOs = BeanCopyUtil.copyList(tagMapper.selectList(null), TagDTO.class);
        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(RedisConstant.ARTICLE_VIEWS_COUNT, 0, 4);
        AuroraAdminInfoDTO auroraAdminInfoDTO = AuroraAdminInfoDTO.builder()
                .articleStatisticsDTOs(articleStatisticsDTOs)
                .tagDTOs(tagDTOs)
                .viewsCount(viewsCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryDTOs(categoryDTOs)
                .uniqueViewDTOs(uniqueViews)
                .build();
        if (CollectionUtils.isNotEmpty(articleMap)) {
            List<ArticleRankDTO> articleRankDTOList = listArticleRank(articleMap);
            auroraAdminInfoDTO.setArticleRankDTOs(articleRankDTOList);
        }
        return auroraAdminInfoDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        WebsiteConfig websiteConfig = WebsiteConfig.builder()
                .id(CommonConstant.DEFAULT_CONFIG_ID)
                .config(JSON.toJSONString(websiteConfigVO))
                .build();
        websiteConfigMapper.updateById(websiteConfig);
        redisService.del(RedisConstant.WEBSITE_CONFIG);
    }

    @Override
    public WebsiteConfigDTO getWebsiteConfig() {
        WebsiteConfigDTO websiteConfigDTO;
        Object websiteConfig = redisService.get(RedisConstant.WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigDTO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigDTO.class);
        } else {
            String config = websiteConfigMapper.selectById(CommonConstant.DEFAULT_CONFIG_ID).getConfig();
            websiteConfigDTO = JSON.parseObject(config, WebsiteConfigDTO.class);
            redisService.set(RedisConstant.WEBSITE_CONFIG, config);
        }
        return websiteConfigDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAbout(AboutVO aboutVO) {
        About about = About.builder()
                .id(CommonConstant.DEFAULT_ABOUT_ID)
                .content(JSON.toJSONString(aboutVO))
                .build();
        aboutMapper.updateById(about);
        redisService.del(RedisConstant.ABOUT);
    }

    @Override
    public AboutDTO getAbout() {
        AboutDTO aboutDTO;
        Object about = redisService.get(RedisConstant.ABOUT);
        if (Objects.nonNull(about)) {
            aboutDTO = JSON.parseObject(about.toString(), AboutDTO.class);
        } else {
            String content = aboutMapper.selectById(CommonConstant.DEFAULT_ABOUT_ID).getContent();
            aboutDTO = JSON.parseObject(content, AboutDTO.class);
            redisService.set(RedisConstant.ABOUT, content);
        }
        return aboutDTO;
    }

    private List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap) {
        List<Integer> articleIds = new ArrayList<>(articleMap.size());
        articleMap.forEach((key, value) -> articleIds.add((Integer) key));
        return articleMapper.selectList(new LambdaQueryWrapper<Article>()
                        .select(Article::getId, Article::getArticleTitle)
                        .in(Article::getId, articleIds))
                .stream().map(article -> ArticleRankDTO.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewsCount(articleMap.get(article.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankDTO::getViewsCount).reversed())
                .collect(Collectors.toList());
    }

}
