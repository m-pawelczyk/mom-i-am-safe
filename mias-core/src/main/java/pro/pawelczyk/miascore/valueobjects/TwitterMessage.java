package pro.pawelczyk.miascore.valueobjects;

import pro.pawelczyk.miascore.messages.TwitterMessageDTO;

import java.time.Instant;
import java.util.UUID;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 02.07.2020
 * created TwitterMessage in pro.pawelczyk.miascore.valueobjects
 * in project mias-core
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

    public TwitterMessageDTO createDTO() {
        return new TwitterMessageDTO(uuid.toString(), timestamp.toString(), account, message);
    }
}
