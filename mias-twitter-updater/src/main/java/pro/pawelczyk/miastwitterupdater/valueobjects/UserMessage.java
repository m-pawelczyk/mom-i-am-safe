package pro.pawelczyk.miastwitterupdater.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import pro.pawelczyk.miastwitterupdater.messages.UserMessageDTO;

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
    private final SenderType senderType;
    private final String messageText;


    public UserMessage(UUID uuid, Instant timestamp, String senderId, SenderType senderType, String messageText) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.senderType = senderType;
        this.messageText = messageText;
    }

    public UserMessage(UserMessageDTO userMessageDTO) {
        this(UUID.fromString(userMessageDTO.getUuid()),
                Instant.parse(userMessageDTO.getTimestamp()),
                userMessageDTO.getSenderId(),
                userMessageDTO.getSenderType(),
                userMessageDTO.getMessageText());
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

    public String getMessageText() {
        return messageText;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
