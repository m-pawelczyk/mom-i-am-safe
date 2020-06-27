package pro.pawelczyk.miasrestgate.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import pro.pawelczyk.miasrestgate.messages.UserMessageDTO;

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
    private final SenderType senderType;
    private final String messageText;

    public UserMessage(SMSMessage smsMessage) {
        this.uuid = UUID.randomUUID();
        this.timestamp = Instant.now();
        this.senderId = smsMessage.getPhoneNumber();
        this.senderType = SenderType.SMS;
        this.messageText = smsMessage.getMessageText();
    }

    public UserMessageDTO createDTO() {
        return new UserMessageDTO(
                getUuidString(),
                getTimestampString(),
                senderId,
                senderType,
                messageText);
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
