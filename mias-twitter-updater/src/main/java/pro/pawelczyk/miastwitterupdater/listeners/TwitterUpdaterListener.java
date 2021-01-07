package pro.pawelczyk.miastwitterupdater.listeners;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pro.pawelczyk.miastwitterupdater.config.RabbitConfig;
import pro.pawelczyk.miastwitterupdater.messages.TwitterMessageDTO;
import pro.pawelczyk.miastwitterupdater.services.TwitterApiService;
import pro.pawelczyk.miastwitterupdater.valueobjects.TwitterMessage;

import java.time.Instant;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 19.06.2020
 * created TwitterUpdateListener in pro.pawelczyk.miastwitterupdater.listeners
 * in project mias-twitter-updater
 */
@Slf4j
@AllArgsConstructor
@Component
public class TwitterUpdaterListener {

    TwitterApiService twitterApiService;

    @RabbitListener(queues = RabbitConfig.queueName)
    public void receive(TwitterMessageDTO twitterMessageDTO) {
        TwitterMessage twitterMessage = new TwitterMessage(twitterMessageDTO);
        String twitterStatus = twitterApiService.postTweet(twitterMessage.getMessage());

        log.info("twitter update status: " + twitterStatus + " for uuid: " + twitterMessageDTO.getUuid());
        if (twitterStatus.equals("OK")) {
            long messageLife = Instant.now().toEpochMilli() - twitterMessage.getTimestamp().toEpochMilli();
            log.info("twitter updated with message: " + twitterMessage.getMessage() + " in: " + messageLife + " millis " +
                    "for uuid: " + twitterMessageDTO.getUuid());
        }
    }
}


