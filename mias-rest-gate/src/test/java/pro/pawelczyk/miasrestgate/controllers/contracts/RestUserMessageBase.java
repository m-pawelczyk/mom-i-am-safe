package pro.pawelczyk.miasrestgate.controllers.contracts;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import pro.pawelczyk.miasrestgate.api.v1.model.AcceptedMessageDTO;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;
import pro.pawelczyk.miasrestgate.controllers.UserMessageController;
import pro.pawelczyk.miasrestgate.services.UserMessageService;
import pro.pawelczyk.miasrestgate.services.UserMessageServiceImpl;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;

public class RestUserMessageBase {

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new UserMessageController(mockedUserMessageService()));
    }

    private UserMessageService mockedUserMessageService() {
        UserMessageServiceImpl userMessageService = BDDMockito.mock(UserMessageServiceImpl.class);

        given(userMessageService.createAndRedirectSMSMessage(any(SMSMessageDTO.class)))
                .willReturn(new AcceptedMessageDTO(UUID.randomUUID().toString(), Instant.now().toString(), "SENDER_ID", "MESSAGE"));
        given(userMessageService.createAndRedirectSMSMessage(argThat(message ->
                message.getMessageText() == null)))
                .willThrow(IllegalArgumentException.class);

        return userMessageService;
    }
}
