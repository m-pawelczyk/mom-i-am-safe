package pro.pawelczyk.miascore.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.List;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 22.06.2020
 * created Trip in pro.pawelczyk.miascore.model
 * in project mias-core
 */
@Document
public class Trip {

    @MongoId
    private String id;
    private String name;
    private String ownerId;
    // TODO - check how it will be stored in MongoDB
    private Instant startDate;
    private Instant stopDate;
    private List<String> positions;
}
