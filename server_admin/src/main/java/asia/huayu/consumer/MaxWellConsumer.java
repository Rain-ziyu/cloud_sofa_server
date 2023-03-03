package asia.huayu.consumer;


import asia.huayu.entity.Article;
import asia.huayu.mapper.ElasticsearchMapper;
import asia.huayu.model.dto.ArticleSearchDTO;
import asia.huayu.model.dto.MaxwellDataDTO;
import asia.huayu.util.BeanCopyUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static asia.huayu.constant.RabbitMQConstant.MAXWELL_QUEUE;

/**
 * @author User
 * MAXWELL将数据库的更新等消息读取到RabbitMQ中由我们消费
 */
@Component
@RabbitListener(queues = MAXWELL_QUEUE)
public class MaxWellConsumer {

    @Autowired
    private ElasticsearchMapper elasticsearchMapper;

    @RabbitHandler
    public void process(byte[] data) {
        MaxwellDataDTO maxwellDataDTO = JSON.parseObject(new String(data), MaxwellDataDTO.class);
        Article article = JSON.parseObject(JSON.toJSONString(maxwellDataDTO.getData()), Article.class, JSONReader.Feature.SupportSmartMatch);
        switch (maxwellDataDTO.getType()) {
            case "bootstrap-insert":
                //     增加适配maxwell初始化写入的数据
            case "insert":
                //     注意虽然这里什么都没写，但是这是利用了switch的特性 即步入到update
            case "update":
                elasticsearchMapper.save(BeanCopyUtil.copyObject(article, ArticleSearchDTO.class));
                break;
            case "delete":
                elasticsearchMapper.deleteById(article.getId());
                break;
            default:
                break;
        }
    }
}