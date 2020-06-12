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
    private final String messageText;

    public SMSMessage(String phoneNumber, String message) {
        if(phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("phoneNumber could not be empty");
        }

        if(message == null || message.isBlank() || message.length() > 160) {
            throw new IllegalArgumentException("Too long single SMS message:, " +
                    ((message != null) ? message.length() : ""));
        }

        this.phoneNumber = phoneNumber;
        this.messageText = message;
    }
}
