package pro.pawelczyk.miascore.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.pawelczyk.miascore.model.User;
import pro.pawelczyk.miascore.repositories.UserRepository;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 08.07.2020
 * created DataLoader in pro.pawelczyk.miascore.bootstrap
 * in project mias-core
 */
@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    public static final String TWITTER_ACCOUNT = "m_pawelczyk_";
    public static final String PHONE_NUMBER_PREFIX = "40050060";
    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(userRepository.count().block() == 0) {
            log.info("load users to db: " + loadUsers());
        }
    }

    private long loadUsers() {
        User user;
        for (int i = 0; i < 9; i++) {
            user = new User();
            user.setPhoneNumber(PHONE_NUMBER_PREFIX + i);
            user.setTwitterAccount(TWITTER_ACCOUNT + i);
            userRepository.save(user).block();
        }

        return userRepository.count().block();
    }
}
