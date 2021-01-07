package pro.pawelczyk.miasrestgate.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pro.pawelczyk.miasrestgate.api.v1.model.AcceptedMessageDTO;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pro.pawelczyk.miasrestgate.message.AbstractRestControllerTest.asJsonString;

class UserMessageControllerTest extends AbstractUserMessageControllerTest {

    @Mock
    UserMessageService userMessageService;

    @InjectMocks
    UserMessageController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new UserMessageController(userMessageService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void createNewMessageFromSMS() throws Exception {
        // given
        SMSMessageDTO smsMessageDTO = aSmsMessageDTO();
        AcceptedMessageDTO acceptedMessageDTO = aAcceptedMessageDTO();

        when(userMessageService.createAndRedirectSMSMessage(smsMessageDTO)).thenReturn(acceptedMessageDTO);

        // then
        mockMvc.perform(post(UserMessageController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(smsMessageDTO))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid", equalTo(UUID)))
                .andExpect(jsonPath("$.timestamp", equalTo(TIMESTAMP)))
                .andExpect(jsonPath("$.senderId", equalTo(SENDER_ID)))
                .andExpect(jsonPath("$.messageText", equalTo(MESSAGE_TEXT)));
    }
}