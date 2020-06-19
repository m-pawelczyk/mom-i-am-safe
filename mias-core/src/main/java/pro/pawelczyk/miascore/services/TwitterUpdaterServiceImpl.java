package pro.pawelczyk.miascore.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pro.pawelczyk.miascore.config.RabbitConfig;
import pro.pawelczyk.miascore.valueobjects.UserMessage;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 19.06.2020
 * created TwitterUpdaterServiceImpl in pro.pawelczyk.miascore.services
 * in project mias-core
 */
@Slf4j
@Service
public class TwitterUpdaterServiceImpl implements TwitterUpdaterService {

    private RabbitTemplate rabbitTemplate;

    public TwitterUpdaterServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendTwitterUpdate(UserMessage userMessage) {
        rabbitTemplate.convertAndSend(RabbitConfig.twitterUpdaterQueueName, userMessage);
    }
}
