package pro.pawelczyk.miascore.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.List;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 22.06.2020
 * created User in pro.pawelczyk.miascore.model
 * in project mias-core
 */
@NoArgsConstructor
@Data
@Document
public class User {

    @MongoId
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    @Indexed(unique = true)
    private String phoneNumber;

    private String twitterAccount;
    private Position lastPosition;
    private Instant lastMessageTimestamp;
    private ObjectId tripId;
    private List<String> tripCollection;

}
