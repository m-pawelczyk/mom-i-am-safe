package pro.pawelczyk.miasrestgate.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 11.06.2020
 * created Message in pro.pawelczyk.miasrestgate.valueobjects
 * in project mias-rest-gate
 */
@ToString
public class UserMessage {

    private final UUID uuid;
    private final Instant timestamp;
    private final String senderId;
    private final String messageText;

    public UserMessage(SMSMessage smsMessage) {
        this.uuid = UUID.randomUUID();
        this.timestamp = Instant.now();
        this.senderId = smsMessage.getPhoneNumber();
        this.messageText = smsMessage.getMessageText();
    }


    @JsonProperty(value = "uuid")
    public UUID getUuidString() {
        return uuid;
    }

    @JsonProperty(value = "timestamp")
    public String getTimestampString() {
        return timestamp.toString();
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessageText() {
        return messageText;
    }
}
