package pro.pawelczyk.miascore.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 18.06.2020
 * created Message in pro.pawelczyk.miascore.valueobjects
 * in project mias-core
 */
@ToString
public class UserMessage {

    private final UUID uuid;
    private final Instant timestamp;
    private final String senderId;
    private final String messageText;


    public UserMessage(@JsonProperty(value = "uuid") String uuid,
                       @JsonProperty(value = "timestamp") String timestamp,
                       @JsonProperty(value = "senderId") String senderId,
                       @JsonProperty(value = "messageText") String messageText) {
        this(UUID.fromString(uuid),
                Instant.parse(timestamp),
                senderId,
                messageText);
    }

    public UserMessage(UUID uuid, Instant timestamp, String senderId, String messageText) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.messageText = messageText;
    }

    //
//    public String getUuidString() {
//        return uuid.toString();
//    }
//
//    public String getTimestampString() {
//        return timestamp.toString();
//    }
//
//    public String getSenderId() {
//        return senderId;
//    }
//
//    public String getMessageText() {
//        return messageText;
//    }
}
