package pro.pawelczyk.miasrestgate.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import pro.pawelczyk.miasrestgate.api.v1.model.AcceptedMessageDTO;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;
import pro.pawelczyk.miasrestgate.messages.UserMessageDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserMessageServiceImplTest {

    public static final String SENDER_ID = "5000300400";
    public static final String MESSAGE_TEXT = "test message";

    @Mock
    RabbitTemplate rabbitTemplate;

    @InjectMocks
    UserMessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        messageService = new UserMessageServiceImpl(rabbitTemplate);
    }

    @Test
    void testCreateAndRedirectSMSMessage() {
        // given
        SMSMessageDTO smsMessageDTO = new SMSMessageDTO(SENDER_ID, MESSAGE_TEXT);
        // when
        AcceptedMessageDTO acceptedMessageDTO = messageService.createAndRedirectSMSMessage(smsMessageDTO);
        // than
        assertEquals(SENDER_ID, acceptedMessageDTO.getSenderId());
        assertEquals(MESSAGE_TEXT, acceptedMessageDTO.getMessageText());
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), Mockito.any(UserMessageDTO.class));
    }
}