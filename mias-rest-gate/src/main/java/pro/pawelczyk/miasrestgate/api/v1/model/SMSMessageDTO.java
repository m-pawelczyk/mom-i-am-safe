package pro.pawelczyk.miasrestgate.api.v1.model;

import pro.pawelczyk.miasrestgate.valueobjects.SMSMessage;

import java.time.Instant;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 11.06.2020
 * created SingleSMSMessage in pro.pawelczyk.miasrestgate.api.v1
 * in project mias-rest-gate
 */
public class SMSMessageDTO {

    private String messageUUID;
    private Instant timestamp;
    private SMSMessage smsMessage;
}
