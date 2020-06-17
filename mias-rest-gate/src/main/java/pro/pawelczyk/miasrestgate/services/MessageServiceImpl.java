package pro.pawelczyk.miasrestgate.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pro.pawelczyk.miasrestgate.MiasRestGateApplication;
import pro.pawelczyk.miasrestgate.api.v1.model.MessageDTO;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;
import pro.pawelczyk.miasrestgate.valueobjects.Message;
import pro.pawelczyk.miasrestgate.valueobjects.SMSMessage;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 11.06.2020
 * created MessageService in pro.pawelczyk.miasrestgate.services
 * in project mias-rest-gate
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    private RabbitTemplate rabbitTemplate;

    public MessageServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public MessageDTO createAndRedirectSMSMessage(SMSMessageDTO smsMessageDTO) {
        SMSMessage smsMessage = new SMSMessage(smsMessageDTO.getPhoneNumber(), smsMessageDTO.getMessageText());
        log.info("receive valid sms message: " + smsMessage.toString());
        Message message = new Message(smsMessage);
        rabbitTemplate.convertAndSend(MiasRestGateApplication.queueName, message.getMessageText());
        log.info("redirect sms message to queue: " + message.toString());
        return new MessageDTO(
                message.getUuidString(),
                message.getTimestampString(),
                message.getSenderId(),
                message.getMessageText());
    }
}
