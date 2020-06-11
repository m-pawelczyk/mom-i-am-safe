package pro.pawelczyk.miasrestgate.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMSMessageTest {

    public static final String PHONE_NUMBER = "500600700";
    public static final String MESSAGE_TEXT = "Hello World!";

    @Test
    public void testCreateValidateMessage() {
        // when
        SMSMessage message = new SMSMessage(PHONE_NUMBER, MESSAGE_TEXT);
        // than
        assertEquals(PHONE_NUMBER, message.getPhoneNumber());
        assertEquals(MESSAGE_TEXT, message.getMessage());
    }

    @Test
    public void testTooLongMessageConstraint() {
        // than
        assertThrows(IllegalArgumentException.class, () -> {
            new SMSMessage(PHONE_NUMBER, getRandomStringWithSize(161));
        });;
    }

    private String getRandomStringWithSize(int size) {
        String availableChars  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz"
                + "0123456789";

        StringBuilder sb = new StringBuilder(size);

        for (int i = 0; i < size; i++) {
            int index = (int)(availableChars.length() * Math.random());
            sb.append(availableChars.charAt(index));
        }
        return sb.toString();
    }
}