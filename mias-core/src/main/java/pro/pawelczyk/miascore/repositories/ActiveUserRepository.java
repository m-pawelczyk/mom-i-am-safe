package pro.pawelczyk.miascore.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pro.pawelczyk.miascore.model.ActiveUser;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 23.06.2020
 * created ActiveUserRepository in pro.pawelczyk.miascore.repositories
 * in project mias-core
 */
public interface ActiveUserRepository extends ReactiveMongoRepository<ActiveUser, String> {
}
