package pro.pawelczyk.miascore.messageprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pro.pawelczyk.miascore.config.RabbitConfig;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 18.06.2020
 * created MessageListener in pro.pawelczyk.miascore.listeners
 * in project mias-core
 */
@Slf4j
@Component
public class UserMessageListener {

    private final UserMessageProcessorService userMessageProcessorService;

    public UserMessageListener(UserMessageProcessorService userMessageProcessorService) {
        this.userMessageProcessorService = userMessageProcessorService;
    }

    @RabbitListener(queues = RabbitConfig.userMessagesQueueName)
    public void receive(UserMessageDTO userMessageDTO) {
        UserMessage userMessage = new UserMessage(userMessageDTO);
        userMessageProcessorService.processMessage(userMessage);
    }
}
