package pro.pawelczyk.miastwitterupdater.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@Slf4j
@Profile("prod")
@Service
public class TwitterApiServiceImpl implements TwitterApiService {

    @Override
    public String postTweet(String message) {
        Twitter twitter = TwitterFactory.getSingleton();
        Status status = null;
        try {
            status = twitter.updateStatus(message);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        if (status != null) {
            log.info("message published: " + message);
            return "OK";
        }
        return "ERROR";
    }
}
