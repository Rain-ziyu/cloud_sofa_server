package asia.huayu.consumer;


import asia.huayu.entity.Article;
import asia.huayu.entity.UserInfo;
import asia.huayu.model.dto.EmailDTO;
import asia.huayu.service.ArticleService;
import asia.huayu.service.UserInfoService;
import asia.huayu.util.EmailUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static asia.huayu.constant.CommonConstant.TRUE;
import static asia.huayu.constant.RabbitMQConstant.SUBSCRIBE_QUEUE;

/**
 * @author User
 * 订阅邮件推送消费者
 */
@Component
@RabbitListener(queues = SUBSCRIBE_QUEUE)
public class SubscribeConsumer {

    @Value("${website.url}")
    private String websiteUrl;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private EmailUtil emailUtil;

    @RabbitHandler
    public void process(byte[] data) {
        Integer articleId = JSON.parseObject(new String(data), Integer.class);
        Article article = articleService.getOne(new LambdaQueryWrapper<Article>().eq(Article::getId, articleId));
        List<UserInfo> users = userInfoService.list(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getIsSubscribe, TRUE));
        List<String> emails = users.stream().map(UserInfo::getEmail).collect(Collectors.toList());
        for (String email : emails) {
            EmailDTO emailDTO = new EmailDTO();
            Map<String, Object> map = new HashMap<>();
            emailDTO.setEmail(email);
            emailDTO.setSubject("文章订阅");
            emailDTO.setTemplate("common.html");
            String url = websiteUrl + "/articles/" + articleId;
            if (article.getUpdateTime() == null) {
                map.put("content", "花未眠的个人博客发布了新的文章，"
                        + "<a style=\"text-decoration:none;color:#12addb\" href=\"" + url + "\">点击查看</a>");
            } else {
                map.put("content", "花未眠的个人博客对《" + article.getArticleTitle() + "》进行了更新，"
                        + "<a style=\"text-decoration:none;color:#12addb\" href=\"" + url + "\">点击查看</a>");
            }
            emailDTO.setCommentMap(map);
            emailUtil.sendHtmlMail(emailDTO);
        }
    }

}
