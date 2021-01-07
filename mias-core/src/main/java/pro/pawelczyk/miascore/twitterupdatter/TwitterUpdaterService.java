package pro.pawelczyk.miascore.twitterupdatter;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 19.06.2020
 * created TwitterUpdaterService in pro.pawelczyk.miascore.services
 * in project mias-core
 */
public interface TwitterUpdaterService {

    void sendTwitterUpdate(TwitterMessage twitterMessage);
}
