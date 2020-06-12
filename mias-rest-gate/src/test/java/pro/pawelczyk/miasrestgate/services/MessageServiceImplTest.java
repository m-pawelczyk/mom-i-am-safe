package pro.pawelczyk.miasrestgate.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import pro.pawelczyk.miasrestgate.api.v1.model.MessageDTO;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    public static final String SENDER_ID = "5000300400";
    public static final String MESSAGE_TEXT = "test message";

    @Mock
    RabbitTemplate rabbitTemplate;

    @InjectMocks
    MessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        messageService = new MessageServiceImpl(rabbitTemplate);
    }

    @Test
    void testCreateAndRedirectSMSMessage() {
        // given
        SMSMessageDTO smsMessageDTO = new SMSMessageDTO(SENDER_ID, MESSAGE_TEXT);
        // when
        MessageDTO messageDTO = messageService.createAndRedirectSMSMessage(smsMessageDTO);
        // than
        assertEquals(SENDER_ID, messageDTO.getSenderId());
        assertEquals(MESSAGE_TEXT, messageDTO.getMessageText());
    }
}