package pro.pawelczyk.miascore.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import pro.pawelczyk.miascore.config.RabbitConfig;
import pro.pawelczyk.miascore.services.TwitterUpdaterService;
import pro.pawelczyk.miascore.valueobjects.UserMessage;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 18.06.2020
 * created MessageListener in pro.pawelczyk.miascore.listeners
 * in project mias-core
 */
@Slf4j
@Component
public class UserMessageListener {

    private final TwitterUpdaterService twitterUpdaterService;

    public UserMessageListener(TwitterUpdaterService twitterUpdaterService) {
        this.twitterUpdaterService = twitterUpdaterService;
    }

    @RabbitListener(queues = RabbitConfig.userMessagesQueueName)
    public void receive(UserMessage userMessage) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("instance " +
                " [x] Received '" + userMessage + "'");
//        if(userMessage.getMessageText().contains("%")) {
//            throw new AmqpRejectAndDontRequeueException("spadaj janusz");
//        }
        // TODO - do something with message
        twitterUpdaterService.sendTwitterUpdate(userMessage);
        watch.stop();
        log.info("instance " +
                " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }
}
