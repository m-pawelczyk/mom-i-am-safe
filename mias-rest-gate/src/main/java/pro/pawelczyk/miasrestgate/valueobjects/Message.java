package pro.pawelczyk.miasrestgate.valueobjects;

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
public class Message {
    private final UUID uuid;
    private final Instant timestamp;
    private final String senderId;
    private final String messageText;

    public Message(SMSMessage smsMessage) {
        this.uuid = UUID.randomUUID();
        this.timestamp = Instant.now();
        this.senderId = smsMessage.getPhoneNumber();
        this.messageText = smsMessage.getMessageText();
    }

    public String getUuidString() {
        return uuid.toString();
    }

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
