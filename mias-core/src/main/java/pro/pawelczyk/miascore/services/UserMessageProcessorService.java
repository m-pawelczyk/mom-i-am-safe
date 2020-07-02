package pro.pawelczyk.miascore.services;

import pro.pawelczyk.miascore.valueobjects.UserMessage;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 02.07.2020
 * created BlaBlaService in pro.pawelczyk.miascore.services
 * in project mias-core
 */
public interface UserMessageProcessorService {

    void processMessage(UserMessage userMessage);
}
