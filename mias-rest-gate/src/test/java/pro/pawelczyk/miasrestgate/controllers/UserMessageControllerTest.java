package pro.pawelczyk.miasrestgate.controllers;

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
import pro.pawelczyk.miasrestgate.services.UserMessageService;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pro.pawelczyk.miasrestgate.controllers.AbstractRestControllerTest.asJsonString;

class UserMessageControllerTest {

    public static final String UUID = "uuid";
    public static final String TIMESTAMP = "12345678";
    public static final String SENDER_ID = "5000300400";
    public static final String MESSAGE_TEXT = "test message";

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
        SMSMessageDTO smsMessageDTO = new SMSMessageDTO(SENDER_ID, MESSAGE_TEXT);
        AcceptedMessageDTO acceptedMessageDTO = new AcceptedMessageDTO(UUID, TIMESTAMP, SENDER_ID, MESSAGE_TEXT);

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