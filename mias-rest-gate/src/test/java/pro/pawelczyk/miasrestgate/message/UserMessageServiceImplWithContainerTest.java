package pro.pawelczyk.miasrestgate.message;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pro.pawelczyk.miasrestgate.api.v1.model.AcceptedMessageDTO;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;
import pro.pawelczyk.miasrestgate.config.RabbitConfig;
import pro.pawelczyk.miasrestgate.message.impl.UserMessageServiceImpl;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(classes = UserMessageServiceImplWithContainerTest.TestConfig.class)
@ActiveProfiles("container")
@Testcontainers
public class UserMessageServiceImplWithContainerTest {

    @Container
    private static final RabbitMQContainer CONTAINER = new RabbitMQContainer()
            .withReuse(true);

    static {
        CONTAINER.start();
        System.setProperty("RABBIT_PORT", String.valueOf(CONTAINER.getFirstMappedPort()));
    }

    @Test
    void shouldMessageContainPhoneNumberAndSimpleMessage(
            @Autowired UserMessageService notifier,
            @Autowired RabbitTemplate rabbitTemplate) {
        // given
        SMSMessageDTO smsMessageDTO = aSmsMessageDTO();
        // when
        AcceptedMessageDTO acceptedMessageDTO = notifier.createAndRedirectSMSMessage(smsMessageDTO);
        // then
        Message message = rabbitTemplate.receive(RabbitConfig.userMessagesQueueName, 100);
        then(message).isNotNull();
        System.out.println(messageBody(message));
        then(messageBody(message)).contains(acceptedMessageDTO.getUuid())
                .contains(smsMessageDTO.getPhoneNumber())
                .contains(smsMessageDTO.getMessageText());
    }

    @Test
    void shouldMessageContainPhoneNumberAndMessageWithGPSCoordEncoded(
            @Autowired UserMessageService notifier,
            @Autowired RabbitTemplate rabbitTemplate) {
        // given
        SMSMessageDTO smsMessageDTO = aSmsMessageDTOWithGPS();
        // when
        AcceptedMessageDTO acceptedMessageDTO = notifier.createAndRedirectSMSMessage(smsMessageDTO);
        // then
        Message message = rabbitTemplate.receive(RabbitConfig.userMessagesQueueName, 100);
        then(message).isNotNull();
        then(messageBody(message))
                .contains(acceptedMessageDTO.getUuid())
                .contains(smsMessageDTO.getPhoneNumber())
                .contains(smsMessageDTO.getMessageText());
    }

    private SMSMessageDTO aSmsMessageDTO() {
        return new SMSMessageDTO("500600700", "Mom I am safe!");
    }

    private SMSMessageDTO aSmsMessageDTOWithGPS() {
        return new SMSMessageDTO("500600700", "Mom I am safe! Hello Mom! gps$-51.7293565,-72.510806,-10.7");
    }

    private String messageBody(Message message) {
        return new String(message.getBody());
    }

    @Configuration
    @Import(RabbitConfig.class)
    @ImportAutoConfiguration(RabbitAutoConfiguration.class)
    static class TestConfig {

        @Bean
        UserMessageService userMessageService (RabbitTemplate rabbitTemplate) {
            return new UserMessageServiceImpl(rabbitTemplate);
        }
    }
}

