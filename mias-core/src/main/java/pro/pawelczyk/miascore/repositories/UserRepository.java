package pro.pawelczyk.miascore.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pro.pawelczyk.miascore.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 23.06.2020
 * created UserRepository in pro.pawelczyk.miascore.repositories
 * in project mias-core
 */
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    
    Mono<User> findByPhoneNumber(String phoneNumber);

    Flux<User> findUsersByLastMessageTimestampIsStartingWith(Instant lastMessageTimestamp);
}
