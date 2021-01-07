package pro.pawelczyk.miascore.messageprocessor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import pro.pawelczyk.miascore.config.RabbitConfig;
import pro.pawelczyk.miascore.messageprocessor.impl.UserMessageProcessorServiceImpl;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserMessageListenerContractTest.TestConfig.class)
@AutoConfigureStubRunner(ids = {"pro.pawelczyk:mias-rest-gate:+:stubs"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL, consumerName = "UserMessageListener")
public class UserMessageListenerContractTest {

    @Autowired
    StubTrigger stubTrigger;

    @Autowired
    UserMessageProcessorService userMessageProcessorService;

    @Autowired
    UserMessageListener userMessageListener;


    @Test
    public void shouldReceiveNotification() {

        stubTrigger.trigger("user-send-message");

        verify(userMessageProcessorService, times(1)).processMessage(isA(UserMessage.class));
    }

    @Configuration
    @Import(RabbitConfig.class)
    @ImportAutoConfiguration(RabbitAutoConfiguration.class)
    static class TestConfig {

        @Bean
        UserMessageProcessorService userMessageProcessorService() {
            return Mockito.mock(UserMessageProcessorServiceImpl.class);
        }

        @Bean
        UserMessageListener userMessageListener(UserMessageProcessorService userMessageProcessorService) {
            return new UserMessageListener(userMessageProcessorService);
        }
    }
}
