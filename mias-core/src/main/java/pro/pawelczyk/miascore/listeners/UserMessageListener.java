package pro.pawelczyk.miascore.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import pro.pawelczyk.miascore.config.RabbitConfig;
import pro.pawelczyk.miascore.model.User;
import pro.pawelczyk.miascore.repositories.UserRepository;
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

    private final UserRepository userRepository;

    public UserMessageListener(TwitterUpdaterService twitterUpdaterService, UserRepository userRepository) {
        this.twitterUpdaterService = twitterUpdaterService;
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = RabbitConfig.userMessagesQueueName)
    public void receive(UserMessage userMessage) {
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("instance " +
                " [x] Received '" + userMessage + "'");
//        if(userMessage.getMessageText().contains("%")) {
//            throw new AmqpRejectAndDontRequeueException("spadaj janusz");
//        }
        User user = new User();
        user.setPhoneNumber(userMessage.getSenderId());
        userRepository
                .save(user)
                .subscribe(result -> {
                    log.info("Entity has been saved: {}", result.getId());
                    twitterUpdaterService.sendTwitterUpdate(userMessage);
                    watch.stop();
                    log.info("instance " + " [x] Done in " + watch.getTotalTimeSeconds() + "s");

                    log.info("repository size after: " + userRepository.count().block());
                });
        log.info("repository size before: " + userRepository.count().block());
    }
}
