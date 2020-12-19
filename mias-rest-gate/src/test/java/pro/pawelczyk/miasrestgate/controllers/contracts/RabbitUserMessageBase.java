package pro.pawelczyk.miasrestgate.controllers.contracts;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;
import pro.pawelczyk.miasrestgate.config.RabbitConfig;
import pro.pawelczyk.miasrestgate.messages.UserMessageDTO;
import pro.pawelczyk.miasrestgate.services.UserMessageService;
import pro.pawelczyk.miasrestgate.services.UserMessageServiceImpl;
import pro.pawelczyk.miasrestgate.valueobjects.SenderType;
import pro.pawelczyk.miasrestgate.valueobjects.UserMessage;

@SpringBootTest(classes = RabbitUserMessageBase.TestConfig.class)
@AutoConfigureMessageVerifier
public class RabbitUserMessageBase  {

    @Autowired
    UserMessageService userMessageService;

    protected void onUserSendMessage() {
        SMSMessageDTO smsMessageDTO = new SMSMessageDTO("400500607", "hej, kurde cycu gdzie gps$50.2135882,18.8671101");
        userMessageService.createAndRedirectSMSMessage(smsMessageDTO);
    }

    @Configuration
    @Import(RabbitConfig.class)
    @ImportAutoConfiguration(RabbitAutoConfiguration.class)
    static class TestConfig {

        @Bean
        UserMessageService userMessageService (RabbitTemplate rabbitTemplate) {
            return new UserMessageServiceImpl(rabbitTemplate) {

                @Override
                protected UserMessageDTO userMessageBody(UserMessage userMessage) {
                    return new UserMessageDTO("55c9644e-d38e-421b-b5c1-299e1e89cc28",
                            "2020-12-19T11:24:52.800153Z",
                            "400500607",
                            SenderType.SMS,
                            "hej, kurde cycu gdzie null gps$50.2135882,18.8671101");
                }
            };
        }
    }
}
