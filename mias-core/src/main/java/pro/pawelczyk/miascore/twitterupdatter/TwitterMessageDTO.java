package pro.pawelczyk.miascore.twitterupdatter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 02.07.2020
 * created TwitterMessageDTO in pro.pawelczyk.miascore.messages
 * in project mias-core
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwitterMessageDTO {

    private String uuid;
    private String timestamp;
    private String account;
    private String message;
}
