package pro.pawelczyk.miastwitterupdater.valueobjects;

import pro.pawelczyk.miastwitterupdater.messages.TwitterMessageDTO;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 02.07.2020
 * created TwitterMessage in pro.pawelczyk.miastwitterupdater.valueobjects
 * in project mias-twitter-updater
 */
public class TwitterMessage {
    private final String account;
    private final String message;

    public TwitterMessage(String account, String message) {

        if(message.length() > 280) {
            throw new IllegalArgumentException("Twitter message could contain maximum 280 characters, but is: "
                    + message.length());
        }
        this.account = account;
        this.message = message;
    }

    public TwitterMessage(TwitterMessageDTO twitterMessageDTO) {
        this(twitterMessageDTO.getAccount(), twitterMessageDTO.getMessage());
    }

    public TwitterMessageDTO createDTO() {
        return new TwitterMessageDTO(account, message);
    }
}
