package asia.huayu;

import asia.huayu.constant.CommonConstant;
import asia.huayu.constant.RabbitMQConstant;
import asia.huayu.model.dto.EmailDTO;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RainZiYu
 * @Date 2023/3/2
 */
@SpringBootTest
public class AuroraSpringBootApplicationTest {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Value("${website.url}")
    private String websiteUrl;

    @Test
    public void testEmail() {
        Map<String, Object> map = new HashMap<>();
        String url = websiteUrl + "/talks/68";
        map.put("content", "userInfo.getNickname()" + "在" + "Objects.requireNonNull(getCommentEnum(comment.getType())).getDesc()"
                + "的评论区@了你，"
                + "<a style=\"text-decoration:none;color:#12addb\" href=\"" + url + "\">点击查看</a>");
        EmailDTO emailDTO = EmailDTO.builder()
                .email("1874300301@qq.com")
                .subject(CommonConstant.MENTION_REMIND)
                .template("common.html")
                .commentMap(map)
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConstant.EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));

    }

    @Test
    public void testOwnerEmail() {
        EmailDTO emailDTO = new EmailDTO();
        String url = websiteUrl + "/talks/68";
        Map<String, Object> map = new HashMap<>();
        emailDTO.setEmail("1874300301@qq.com");
        emailDTO.setSubject(CommonConstant.COMMENREMIND);
        emailDTO.setTemplate("owner.html");

        map.put("time", new Date());
        map.put("url", url);
        map.put("title", "aaaa");
        map.put("nickname", "fromNickname");
        map.put("content", "comment.getCommentContent()");
        emailDTO.setCommentMap(map);
        rabbitTemplate.convertAndSend(RabbitMQConstant.EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));

    }
}
