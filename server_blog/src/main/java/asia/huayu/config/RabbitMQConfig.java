package asia.huayu.config;

import asia.huayu.constant.RabbitMQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue articleQueue() {
        return new Queue(RabbitMQConstant.MAXWELL_QUEUE, true);
    }

    @Bean
    public FanoutExchange maxWellExchange() {
        return new FanoutExchange(RabbitMQConstant.MAXWELL_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingArticleDirect() {
        return BindingBuilder.bind(articleQueue()).to(maxWellExchange());
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(RabbitMQConstant.EMAIL_QUEUE, true);
    }

    @Bean
    public FanoutExchange emailExchange() {
        return new FanoutExchange(RabbitMQConstant.EMAIL_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingEmailDirect() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange());
    }

    @Bean
    public Queue subscribeQueue() {
        return new Queue(RabbitMQConstant.SUBSCRIBE_QUEUE, true);
    }

    @Bean
    public FanoutExchange subscribeExchange() {
        return new FanoutExchange(RabbitMQConstant.SUBSCRIBE_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingSubscribeDirect() {
        return BindingBuilder.bind(subscribeQueue()).to(subscribeExchange());
    }

}
