package pro.pawelczyk.miascore.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 22.06.2020
 * created Position in pro.pawelczyk.miascore.model
 * in project mias-core
 */
@Document
public class Position {

    @MongoId
    private String id;
    private double longitude;
    private double latitude;
    private double altitude;
    // TODO - check how it will be stored in MongoDB
    private Instant timestamp;

}
