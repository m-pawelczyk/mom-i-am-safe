package pro.pawelczyk.miascore.twitterupdatter.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pro.pawelczyk.miascore.config.RabbitConfig;
import pro.pawelczyk.miascore.twitterupdatter.TwitterUpdaterService;
import pro.pawelczyk.miascore.twitterupdatter.TwitterMessage;

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
    public void sendTwitterUpdate(TwitterMessage twitterMessage) {
        rabbitTemplate.convertAndSend(RabbitConfig.twitterUpdaterQueueName, twitterMessage.createDTO());
    }
}
