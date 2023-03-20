package asia.huayu.quartz;

import asia.huayu.common.util.IpUtil;
import asia.huayu.config.RestConfig;
import asia.huayu.constant.CommonConstant;
import asia.huayu.constant.RedisConstant;
import asia.huayu.entity.Article;
import asia.huayu.entity.Resource;
import asia.huayu.entity.RoleResource;
import asia.huayu.entity.UniqueView;
import asia.huayu.mapper.UniqueViewMapper;
import asia.huayu.security.entity.OnlineUser;
import asia.huayu.security.util.SystemValue;
import asia.huayu.service.*;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component("auroraQuartz")
public class AuroraQuartz {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private JobLogService jobLogService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private UniqueViewMapper uniqueViewMapper;


    @Autowired
    private RestConfig.RestService restService;


    @Value("${website.url}")
    private String websiteUrl;

    public void saveUniqueView() {
        Long count = redisService.sSize(RedisConstant.UNIQUE_VISITOR);
        UniqueView uniqueView = UniqueView.builder()
                .createTime(LocalDateTimeUtil.offset(LocalDateTime.now(), -1, ChronoUnit.DAYS))
                .viewsCount(Optional.of(count.intValue()).orElse(0))
                .build();
        uniqueViewMapper.insert(uniqueView);
    }

    public void clear() {
        redisService.del(RedisConstant.UNIQUE_VISITOR);
        redisService.del(RedisConstant.VISITOR_AREA);
    }

    /**
     * 方法statisticalUserArea作用为：
     * 定时刷新用户登录统计区域 在security中协助进行实时的更新  过滤掉部分用户登陆一次未注销
     *
     * @param
     * @return void
     * @throws
     * @author RainZiYu
     */
    public void statisticalUserArea() {
        // 将在线的用户信息存从redis中取出
        Map<String, Object> userMaps = redisService.hGetAll(SystemValue.LOGIN_USER);
        Collection<String> values = userMaps.keySet();
        ArrayList<OnlineUser> onlineUsers = new ArrayList<>();
        for (Object value : values) {
            OnlineUser onlineUser = (OnlineUser) userMaps.get(value);
            // 如果当前时间小于过期时间
            if (DateUtil.compare(onlineUser.getExpireTime(), new Date()) > 0) {
                onlineUsers.add(onlineUser);
            } else {
                // 移除token已经过期的
                redisService.hDel(SystemValue.LOGIN_USER, value);
            }
        }

        Map<String, Long> userAreaMap = onlineUsers
                .stream()
                .map(item -> {
                    if (Objects.nonNull(item) && StringUtils.isNotBlank(item.getIpSource())) {
                        return IpUtil.getIpProvince(item.getIpSource());
                    }
                    return CommonConstant.UNKNOWN;
                })
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));
        redisService.hSetAll(RedisConstant.USER_AREA, userAreaMap);
    }

    public void baiduSeo() {
        List<Integer> ids = articleService.list().stream().map(Article::getId).collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Host", "data.zz.baidu.com");
        headers.add("User-Agent", "curl/7.12.1");
        headers.add("Content-Length", "83");
        headers.add("Content-Type", "text/plain");
        ids.forEach(item -> {
            String url = websiteUrl + "/articles/" + item;
            HttpEntity<String> entity = new HttpEntity<>(url, headers);
            restService.restTemplate.postForObject("https://www.baidu.com", entity, String.class);
        });
    }

    public void clearJobLogs() {
        jobLogService.cleanJobLogs();
    }

    public void importSwagger(String targetUrl, String urlPrefix) {
        List<Resource> resources = resourceService.importSwagger(targetUrl, urlPrefix);
        List<Integer> resourceIds = resources.stream().map(Resource::getId).collect(Collectors.toList());
        List<RoleResource> roleResources = new ArrayList<>();
        // 默认全部赋予管理员可用
        for (Integer resourceId : resourceIds) {
            roleResources.add(RoleResource.builder()
                    .roleId(1)
                    .resourceId(resourceId)
                    .build());
        }
        roleResourceService.saveBatch(roleResources);
    }

}
