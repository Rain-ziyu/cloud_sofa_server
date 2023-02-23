package asia.huayu.service.impl;

import asia.huayu.constant.CommonConstant;
import asia.huayu.constant.RedisConstant;
import asia.huayu.entity.Article;
import asia.huayu.mapper.*;
import asia.huayu.model.dto.AboutDTO;
import asia.huayu.model.dto.AuroraHomeInfoDTO;
import asia.huayu.model.dto.WebsiteConfigDTO;
import asia.huayu.service.AuroraInfoService;
import asia.huayu.service.RedisService;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
    private TalkMapper talkMapper;


    @Autowired
    private AboutMapper aboutMapper;

    @Autowired
    private RedisService redisService;


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
    public WebsiteConfigDTO getWebsiteConfig() {
        WebsiteConfigDTO websiteConfigDTO;
        Object websiteConfig = redisService.get(RedisConstant.WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigDTO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigDTO.class);
        } else {
            String config = websiteConfigMapper.selectById(CommonConstant.DEFAULCONFIG_ID).getConfig();
            websiteConfigDTO = JSON.parseObject(config, WebsiteConfigDTO.class);
            redisService.set(RedisConstant.WEBSITE_CONFIG, config);
        }
        return websiteConfigDTO;
    }


    @Override
    public AboutDTO getAbout() {
        AboutDTO aboutDTO;
        Object about = redisService.get(RedisConstant.ABOUT);
        if (Objects.nonNull(about)) {
            aboutDTO = JSON.parseObject(about.toString(), AboutDTO.class);
        } else {
            String content = aboutMapper.selectById(CommonConstant.DEFAULABOUID).getContent();
            aboutDTO = JSON.parseObject(content, AboutDTO.class);
            redisService.set(RedisConstant.ABOUT, content);
        }
        return aboutDTO;
    }


}
