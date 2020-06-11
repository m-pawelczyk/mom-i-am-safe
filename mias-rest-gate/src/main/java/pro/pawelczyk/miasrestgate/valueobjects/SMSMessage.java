package pro.pawelczyk.miasrestgate.valueobjects;

import lombok.Getter;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 11.06.2020
 * created SMSMessage in pro.pawelczyk.miasrestgate.valueobjects
 * in project mias-rest-gate
 */
@Getter
public class SMSMessage {
    private final String phoneNumber;
    private final String message;

    SMSMessage(String phoneNumber, String message) {
        if(message.length() > 160) {
            throw new IllegalArgumentException("Too long single SMS message:, " + message.length());
        }

        this.phoneNumber = phoneNumber;
        this.message = message;
    }
}
