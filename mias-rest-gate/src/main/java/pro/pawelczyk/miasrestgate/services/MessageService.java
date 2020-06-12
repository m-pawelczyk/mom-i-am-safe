package pro.pawelczyk.miasrestgate.services;

import pro.pawelczyk.miasrestgate.api.v1.model.MessageDTO;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 11.06.2020
 * created MessageService in pro.pawelczyk.miasrestgate.services
 * in project mias-rest-gate
 */
public interface MessageService {

    MessageDTO createAndRedirectSMSMessage(SMSMessageDTO smsMessageDTO);
}
