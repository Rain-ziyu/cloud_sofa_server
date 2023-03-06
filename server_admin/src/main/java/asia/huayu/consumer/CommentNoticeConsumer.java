package asia.huayu.consumer;


import asia.huayu.model.dto.EmailDTO;
import asia.huayu.model.dto.TheWordOfTheDayDTO;
import asia.huayu.model.dto.WebsiteConfigDTO;
import asia.huayu.service.AuroraInfoService;
import asia.huayu.service.HitokotoHttpService;
import asia.huayu.util.EmailUtil;
import com.alibaba.fastjson2.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static asia.huayu.constant.RabbitMQConstant.EMAIL_QUEUE;

@Component
@RabbitListener(queues = EMAIL_QUEUE)
public class CommentNoticeConsumer {

    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    HitokotoHttpService hitokotoHttpService;
    @Autowired
    private AuroraInfoService auroraInfoService;
    @Value("${website.url}")
    private String websiteUrl;

    @RabbitHandler
    public void process(byte[] data) {
        EmailDTO emailDTO = JSON.parseObject(new String(data), EmailDTO.class);
        WebsiteConfigDTO websiteConfig = auroraInfoService.getWebsiteConfig();
        Map<String, Object> commentMap = emailDTO.getCommentMap();
        // 统一放入网站配置信息  在模板中统一展示
        commentMap.put("webUrl", websiteUrl);
        commentMap.put("webName", websiteConfig.getName());
        TheWordOfTheDayDTO theWordOfTheDay = hitokotoHttpService.getTheWordOfTheDay();
        String result = theWordOfTheDay.getHitokoto() + " ——" + theWordOfTheDay.getFrom();
        commentMap.put("theWordOfTheDay", result);
        emailUtil.sendHtmlMail(emailDTO);
    }

}
