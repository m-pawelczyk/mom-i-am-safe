package pro.pawelczyk.miascore.messageprocessor.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pro.pawelczyk.miascore.messageprocessor.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 23.06.2020
 * created UserRepository in pro.pawelczyk.miascore.messageprocessor.repositories
 * in project mias-core
 */
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Flux<User> findByIdNotNullAndTwitterAccountContains(String twitterAccount, Pageable pageable);

    Mono<User> findByPhoneNumber(String phoneNumber);
}
