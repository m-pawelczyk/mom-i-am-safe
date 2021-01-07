package pro.pawelczyk.miascore.messageprocessor.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pro.pawelczyk.miascore.messageprocessor.model.Position;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 23.06.2020
 * created PositionRepository in pro.pawelczyk.miascore.messageprocessor.repositories
 * in project mias-core
 */
public interface PositionRepository extends ReactiveMongoRepository<Position, String> {
}
