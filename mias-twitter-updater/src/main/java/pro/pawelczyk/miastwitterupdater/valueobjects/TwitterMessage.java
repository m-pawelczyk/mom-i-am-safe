package pro.pawelczyk.miastwitterupdater.valueobjects;

import pro.pawelczyk.miastwitterupdater.messages.TwitterMessageDTO;

import java.time.Instant;
import java.util.UUID;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 02.07.2020
 * created TwitterMessage in pro.pawelczyk.miastwitterupdater.valueobjects
 * in project mias-twitter-updater
 */
public class TwitterMessage {
    private final UUID uuid;
    private final Instant timestamp;
    private final String account;
    private final String message;

    public TwitterMessage(UUID uuid, Instant timestamp, String account, String message) {

        if(message.length() > 280) {
            throw new IllegalArgumentException("Twitter message could contain maximum 280 characters, but is: "
                    + message.length());
        }
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.account = account;
        this.message = message;
    }

    public TwitterMessage(TwitterMessageDTO twitterMessageDTO) {
        this(UUID.fromString(twitterMessageDTO.getUuid()),
                Instant.parse(twitterMessageDTO.getTimestamp()),
                twitterMessageDTO.getAccount(),
                twitterMessageDTO.getMessage());
    }

    public TwitterMessageDTO createDTO() {
        return new TwitterMessageDTO(uuid.toString(), timestamp.toString(), account, message);
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
