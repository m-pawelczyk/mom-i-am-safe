package pro.pawelczyk.miascore.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;
import pro.pawelczyk.miascore.config.RabbitConfig;
import pro.pawelczyk.miascore.valueobjects.UserMessage;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 18.06.2020
 * created MessageListener in pro.pawelczyk.miascore.listeners
 * in project mias-core
 */
@Slf4j
public class UserMessageListener {

    @RabbitListener(queues = RabbitConfig.queueName)
    public void receive(UserMessage userMessage) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("instance " +
                " [x] Received '" + userMessage + "'");
//        if(userMessage.getMessageText().contains("%")) {
//            throw new AmqpRejectAndDontRequeueException("spadaj janusz");
//        }
        // TODO - do something with message
        watch.stop();
        log.info("instance " +
                " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }
}
