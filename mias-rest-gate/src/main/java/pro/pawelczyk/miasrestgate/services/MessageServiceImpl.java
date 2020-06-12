package pro.pawelczyk.miasrestgate.services;

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
@Service
public class MessageServiceImpl implements MessageService {

    private RabbitTemplate rabbitTemplate;

    public MessageServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public MessageDTO createAndRedirectSMSMessage(SMSMessageDTO smsMessageDTO) {
        SMSMessage smsMessage = new SMSMessage(smsMessageDTO.getPhoneNumber(), smsMessageDTO.getMessageText());
        Message message = new Message(smsMessage);
        rabbitTemplate.convertAndSend(MiasRestGateApplication.queueName, message.getMessageText());
        return new MessageDTO(
                message.getUuidString(),
                message.getTimestampString(),
                message.getSenderId(),
                message.getMessageText());
    }
}
