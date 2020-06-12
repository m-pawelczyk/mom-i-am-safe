package pro.pawelczyk.miasrestgate.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pro.pawelczyk.miasrestgate.api.v1.model.MessageDTO;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;
import pro.pawelczyk.miasrestgate.services.MessageService;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 11.06.2020
 * created MessageController in pro.pawelczyk.miasrestgate.controllers
 * in project mias-rest-gate
 */
@RestController
@RequestMapping(MessageController.BASE_URL)
public class MessageController {

    public static final String BASE_URL = "/api/v1/messages";

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO createNewMessageFromSMS(@RequestBody SMSMessageDTO smsMessageDTO){
        return messageService.createAndRedirectSMSMessage(smsMessageDTO);
    }
}
