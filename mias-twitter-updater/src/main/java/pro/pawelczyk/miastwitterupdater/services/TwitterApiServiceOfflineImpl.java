package pro.pawelczyk.miastwitterupdater.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Profile("!prod")
@Service
public class TwitterApiServiceOfflineImpl implements TwitterApiService {

    @Override
    public String postTweet(String message) {
        log.info("message published offline: " + message);
        return "OK";
    }
}
