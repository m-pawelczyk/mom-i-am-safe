package pro.pawelczyk.miastwitterupdater.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;
import pro.pawelczyk.miastwitterupdater.config.RabbitConfig;
import pro.pawelczyk.miastwitterupdater.valueobjects.UserMessage;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 19.06.2020
 * created TwitterUpdateListener in pro.pawelczyk.miastwitterupdater.listeners
 * in project mias-twitter-updater
 */
@Slf4j
public class TwitterUpdaterListener {

    @RabbitListener(queues = RabbitConfig.queueName)
    public void receive(UserMessage userMessage) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("instance " +
                " [x] Received '" + userMessage.toString() + "'");
        Thread.sleep(1000);
        // TODO - do something with message
        watch.stop();
        log.info("instance " +
                " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }
}
