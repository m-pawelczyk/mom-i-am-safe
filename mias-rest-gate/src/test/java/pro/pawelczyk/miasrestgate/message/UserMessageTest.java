package pro.pawelczyk.miasrestgate.message;

import org.junit.jupiter.api.Test;
import pro.pawelczyk.miasrestgate.message.SMSMessage;
import pro.pawelczyk.miasrestgate.message.SenderType;
import pro.pawelczyk.miasrestgate.message.UserMessage;
import pro.pawelczyk.miasrestgate.message.UserMessageDTO;

import static org.junit.jupiter.api.Assertions.*;

class UserMessageTest {

    public static final String PHONE_NUMBER = "500600700";
    public static final String MESSAGE_TEXT = "Hello World!";

    @Test
    public void testCreateUserMessageWithSMSType() {
        // given
        SMSMessage smsMessage = new SMSMessage(PHONE_NUMBER, MESSAGE_TEXT);
        // when
        UserMessage userMessage = new UserMessage(smsMessage);
        UserMessageDTO userMessageDTO = userMessage.createDTO();
        // then
        assertEquals(SenderType.SMS, userMessageDTO.getSenderType());
    }
}