package pro.pawelczyk.miastwitterupdater.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 19.06.2020
 * created UserMessage in pro.pawelczyk.miastwitterupdater.valueobjects
 * in project mias-twitter-updater
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
}
