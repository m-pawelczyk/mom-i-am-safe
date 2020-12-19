package pro.pawelczyk.miasrestgate.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 19.06.2020
 * created RabbitConfig in pro.pawelczyk.miasrestgate.config
 * in project mias-rest-gate
 */
@Configuration
public class RabbitConfig {

    public static final String userMessagesQueueName = "user-messages";

    @Bean
    Binding binding() {
        return BindingBuilder
                .bind(userMessageQueue())
                .to(userMessageExchange())
                .with("#");
    }

    @Bean
    DirectExchange userMessageExchange() {
        return new DirectExchange(userMessagesQueueName);
    }

    @Bean
    Queue userMessageQueue() {
        return new Queue(userMessagesQueueName, false);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
