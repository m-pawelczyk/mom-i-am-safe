package pro.pawelczyk.miascore.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
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
@Data
@Document
public class Trip {

    @MongoId
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String name;
    private ObjectId ownerId;
    // TODO - check how it will be stored in MongoDB
    private Instant startDate;
    private Instant stopDate;
    private List<ObjectId> positions;
}
