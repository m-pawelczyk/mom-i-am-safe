package pro.pawelczyk.miascore.config;

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
 * on 18.06.2020
 * created RabbitConfig in pro.pawelczyk.miascore.config
 * in project mias-core
 */
@Configuration
public class RabbitConfig {

    public static final String userMessagesQueueName = "user-messages";
    public static final String twitterUpdaterQueueName = "twitter-updater";

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
    Queue twitterUpdaterQueue() {
        return new Queue(twitterUpdaterQueueName, false);
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

//    @Override
//    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
//        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
//    }
//
//    @Bean
//    MessageHandlerMethodFactory messageHandlerMethodFactory() {
//        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
//        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
//        return messageHandlerMethodFactory;
//    }
//
//    @Bean
//    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
//        return new MappingJackson2MessageConverter();
//    }
}
