package pro.pawelczyk.miastwitterupdater.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;
import pro.pawelczyk.miastwitterupdater.config.RabbitConfig;
import pro.pawelczyk.miastwitterupdater.messages.TwitterMessageDTO;
import pro.pawelczyk.miastwitterupdater.valueobjects.TwitterMessage;

import java.time.Instant;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 19.06.2020
 * created TwitterUpdateListener in pro.pawelczyk.miastwitterupdater.listeners
 * in project mias-twitter-updater
 */
@Slf4j
public class TwitterUpdaterListener {

    @RabbitListener(queues = RabbitConfig.queueName)
    public void receive(TwitterMessageDTO twitterMessageDTO) throws InterruptedException {
//        StopWatch watch = new StopWatch();
//        watch.start();
        TwitterMessage twitterMessage = new TwitterMessage(twitterMessageDTO);
        long messageLife = Instant.now().toEpochMilli() - twitterMessage.getTimestamp().toEpochMilli();
        log.info("twitter updated with message: " + twitterMessage.getMessage() + " in: " + messageLife + " millis");
//        watch.stop();
//        log.info("instance " +
//                " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }
}
