package pro.pawelczyk.miascore.valueobjects;

import java.time.Instant;
import java.util.UUID;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 02.07.2020
 * created ProcessedMessage in pro.pawelczyk.miascore.valueobjects
 * in project mias-core
 */
public class ProcessedMessage extends AbstractProcessMessage {
    private final UUID uuid;
    private final Instant timestamp;
    private final String senderId;
    private final SenderType senderType;
    private final MessageText messageText;

    public ProcessedMessage(UUID uuid, Instant timestamp, String senderId, SenderType senderType, MessageText messageText) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.senderType = senderType;
        this.messageText = messageText;
    }



    public TwitterMessage twittMessage(String twitterAccount) {
        return new TwitterMessage(uuid, timestamp, twitterAccount, messageText.getMessageTextString());
    }

    protected UUID getUuid() {
        return uuid;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    protected MessageText getMessageText() {
        return messageText;
    }
}
