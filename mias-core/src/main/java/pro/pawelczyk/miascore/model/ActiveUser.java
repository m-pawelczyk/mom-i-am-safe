package pro.pawelczyk.miascore.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 22.06.2020
 * created ActiveUser in pro.pawelczyk.miascore.model
 * in project mias-core
 */
@NoArgsConstructor
@Data
@Document
public class ActiveUser {

    @MongoId
    private String id;
    private String userId;
    private Position position;
}
