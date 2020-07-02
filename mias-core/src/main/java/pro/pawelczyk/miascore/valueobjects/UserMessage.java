package pro.pawelczyk.miascore.valueobjects;

import lombok.ToString;
import pro.pawelczyk.miascore.messages.UserMessageDTO;

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
    private final SenderType senderType;
    private final MessageText messageText;


    public UserMessage(UserMessageDTO userMessageDTO) {
        this(UUID.fromString(userMessageDTO.getUuid()),
                Instant.parse(userMessageDTO.getTimestamp()),
                userMessageDTO.getSenderId(),
                userMessageDTO.getSenderType(),
                new MessageText(userMessageDTO.getMessageText()));
    }

    public UserMessage(UUID uuid, Instant timestamp, String senderId, SenderType senderType, MessageText messageText) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.senderType = senderType;
        this.messageText = messageText;
    }

    public UserMessageDTO createDTO() {
        return new UserMessageDTO(
                getUuidString(),
                getTimestampString(),
                senderId,
                senderType,
                getMessageText());
    }

    public ProcessedMessage process() {
        if(messageText.containsCoordinatesData()) {
            return new ProcessedMessageWithCord(uuid, timestamp, senderId, senderType, messageText, messageText.extractCoordinatesIfExists().get());
        } else {
            return new ProcessedMessage(uuid, timestamp, senderId, senderType, messageText);
        }
    }

    // TODO - should be remove - not need to be open
    public UUID getUuid() {
        return uuid;
    }

    // TODO - should be remove - not need to be open
    public Instant getTimestamp() {
        return timestamp;
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

    public SenderType getSenderType() {
        return senderType;
    }

    public String getMessageText() {
        return messageText.getMessageTextString();
    }
}
