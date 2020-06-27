package pro.pawelczyk.miasrestgate.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pro.pawelczyk.miasrestgate.api.v1.model.AcceptedMessageDTO;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;
import pro.pawelczyk.miasrestgate.services.UserMessageService;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 11.06.2020
 * created MessageController in pro.pawelczyk.miasrestgate.controllers
 * in project mias-rest-gate
 */
@Api(description = "All types message handler") // "value=" is not working
@Slf4j
@RestController
@RequestMapping(UserMessageController.BASE_URL)
public class UserMessageController {

    public static final String BASE_URL = "/api/v1/messages";

    private final UserMessageService userMessageService;

    public UserMessageController(UserMessageService userMessageService) {
        this.userMessageService = userMessageService;
    }

    @ApiOperation(value = "Create new SMS message in system.",
            notes = "It helps to create fake SMS message without phone or SMS gate. " +
                    "This message will be handled as normal SMS from phone")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AcceptedMessageDTO createNewMessageFromSMS(@RequestBody SMSMessageDTO smsMessageDTO) {
        log.info("receive sms message: " + smsMessageDTO.toString());
        return userMessageService.createAndRedirectSMSMessage(smsMessageDTO);
    }
}
