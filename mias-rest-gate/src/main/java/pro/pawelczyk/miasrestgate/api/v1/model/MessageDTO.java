package pro.pawelczyk.miasrestgate.api.v1.model;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 11.06.2020
 * created MessageDTO in pro.pawelczyk.miasrestgate.api.v1.model
 * in project mias-rest-gate
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private String uuid;
    private String timestamp;
    private String senderId;
    private String messageText;
}
