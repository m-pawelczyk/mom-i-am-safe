package pro.pawelczyk.miasrestgate.api.v1.model;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 11.06.2020
 * created SingleSMSMessage in pro.pawelczyk.miasrestgate.api.v1
 * in project mias-rest-gate
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SMSMessageDTO {

    @ApiParam(value = "user phone number", required = true)
    private String phoneNumber;

    @ApiParam(value = "sms message text", required = true)
    private String messageText;

//    @JsonCreator
//    public SMSMessageDTO(@JsonProperty("phoneNumber") String phoneNumber,
//                         @JsonProperty("message") String message) {
//        this.phoneNumber = phoneNumber;
//        this.message = message;
//    }
}

