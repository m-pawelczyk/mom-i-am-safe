package pro.pawelczyk.miascore.services;

import pro.pawelczyk.miascore.messages.TwitterMessageDTO;
import pro.pawelczyk.miascore.valueobjects.TwitterMessage;
import pro.pawelczyk.miascore.valueobjects.UserMessage;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 19.06.2020
 * created TwitterUpdaterService in pro.pawelczyk.miascore.services
 * in project mias-core
 */
public interface TwitterUpdaterService {

    void sendTwitterUpdate(TwitterMessage twitterMessage);
}
