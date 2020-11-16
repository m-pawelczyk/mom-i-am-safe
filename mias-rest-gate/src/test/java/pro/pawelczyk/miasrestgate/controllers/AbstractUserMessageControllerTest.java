package pro.pawelczyk.miasrestgate.controllers;

import pro.pawelczyk.miasrestgate.api.v1.model.AcceptedMessageDTO;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;

public abstract class AbstractUserMessageControllerTest {
    public static final String UUID = "uuid";
    public static final String TIMESTAMP = "12345678";
    public static final String SENDER_ID = "5000300400";
    public static final String MESSAGE_TEXT = "test message";

    public SMSMessageDTO aSmsMessageDTO() {
        return new SMSMessageDTO(SENDER_ID, MESSAGE_TEXT);
    }

    public AcceptedMessageDTO aAcceptedMessageDTO() {
        return new AcceptedMessageDTO(UUID, TIMESTAMP, SENDER_ID, MESSAGE_TEXT);
    }
}
