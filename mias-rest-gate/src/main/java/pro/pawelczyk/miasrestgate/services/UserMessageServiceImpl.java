package pro.pawelczyk.miasrestgate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pro.pawelczyk.miasrestgate.api.v1.model.AcceptedMessageDTO;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;
import pro.pawelczyk.miasrestgate.config.RabbitConfig;
import pro.pawelczyk.miasrestgate.messages.UserMessageDTO;
import pro.pawelczyk.miasrestgate.valueobjects.SenderType;
import pro.pawelczyk.miasrestgate.valueobjects.UserMessage;
import pro.pawelczyk.miasrestgate.valueobjects.SMSMessage;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 11.06.2020
 * created MessageService in pro.pawelczyk.miasrestgate.services
 * in project mias-rest-gate
 */
@Slf4j
@Service
public class UserMessageServiceImpl implements UserMessageService {

    private RabbitTemplate rabbitTemplate;

    public UserMessageServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public AcceptedMessageDTO createAndRedirectSMSMessage(SMSMessageDTO smsMessageDTO) {
        UserMessage userMessage = new UserMessage(
                new SMSMessage(smsMessageDTO.getPhoneNumber(), smsMessageDTO.getMessageText()));
        log.info("create valid user message: " + userMessage.toString());
        UserMessageDTO userMessageDTO = userMessage.createDTO();
        rabbitTemplate.convertAndSend(RabbitConfig.queueName, userMessageDTO);
        log.info("redirect sms message to queue: " + userMessageDTO.toString());
        return new AcceptedMessageDTO(
                userMessage.getUuidString(),
                userMessage.getTimestampString(),
                userMessage.getSenderId(),
                userMessage.getMessageText());
    }
}
