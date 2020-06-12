package pro.pawelczyk.miasrestgate;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MiasRestGateApplication {

    public static final String queueName = "user-messages";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    public static void main(String[] args) {
        SpringApplication.run(MiasRestGateApplication.class, args);
    }

}
