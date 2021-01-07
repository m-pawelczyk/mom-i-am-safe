package pro.pawelczyk.miascore.messageprocessor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 27.06.2020
 * created UserMessageDTO in pro.pawelczyk.miascore.messages
 * in project mias-core
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageDTO {

    private String uuid;
    private String timestamp;
    private String senderId;
    private SenderType senderType;
    private String messageText;
}
