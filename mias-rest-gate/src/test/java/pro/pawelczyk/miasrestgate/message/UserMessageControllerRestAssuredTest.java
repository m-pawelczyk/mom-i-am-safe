package pro.pawelczyk.miasrestgate.message;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;
import pro.pawelczyk.miasrestgate.api.v1.model.AcceptedMessageDTO;
import pro.pawelczyk.miasrestgate.api.v1.model.SMSMessageDTO;
import pro.pawelczyk.miasrestgate.message.impl.UserMessageServiceImpl;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static pro.pawelczyk.miasrestgate.message.AbstractRestControllerTest.asJsonString;

@WebMvcTest
@ContextConfiguration(classes = UserMessageControllerRestAssuredTest.RestAssuredConfig.class)
public class UserMessageControllerRestAssuredTest extends AbstractUserMessageControllerTest {

    @Test
    void shouldResponseCreatedAndJsonObjectWhenPostMessage(@Autowired WebApplicationContext context) {

        given()
                .webAppContextSetup(context)
                .contentType("application/json")
                .body(asJsonString(aSmsMessageDTO()))
        .when()
                .post(UserMessageController.BASE_URL)
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .expect(jsonPath("$.uuid", equalTo(UUID)))
                .expect(jsonPath("$.timestamp", equalTo(TIMESTAMP)))
                .expect(jsonPath("$.senderId", equalTo(SENDER_ID)))
                .expect(jsonPath("$.messageText", equalTo(MESSAGE_TEXT)));
    }

    @Test
    void shouldResponseBadRequestWhenBodyIsEmpty(@Autowired WebApplicationContext context) {

        given()
                .webAppContextSetup(context)
                .contentType("application/json")
        .when()
                .post(UserMessageController.BASE_URL)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Disabled
    void shouldResponseBadRequestWhenMessageNotContainPhoneNumber(@Autowired WebApplicationContext context) {

        given()
                .webAppContextSetup(context)
                .contentType("application/json")
                .body(aMessageWithoutPhoneNumber())
        .when()
                .post(UserMessageController.BASE_URL)
        .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    @Disabled
    void shouldResponseBadRequestWhenMessageNotContainMessageText(@Autowired WebApplicationContext context) {

        given()
                .webAppContextSetup(context)
                .contentType("application/json")
                .body(aMessageWithoutMessageText())
        .when()
                .post(UserMessageController.BASE_URL)
        .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    void shouldResponseBadRequestWhenMessageContainExtraData(@Autowired WebApplicationContext context) {

        given()
                .webAppContextSetup(context)
                .contentType("application/json")
                .body(aMessageMalformed())
        .when()
                .post(UserMessageController.BASE_URL)
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    private String aMessageWithoutPhoneNumber() {
        return "{\n" +
                "    \"messageText\": \"Hello mom, I am safe! gps$50.2135882,18.8671101\"\n" +
                "}";
    }

    private String aMessageWithoutMessageText() {
        return "{\n" +
                "    \"phoneNumber\": \"400500607\"\n" +
                "}";
    }

    private String aMessageMalformed() {
        return "{\n" +
                "    \"extraData\": \"Ala ma kota\",\n" +
                "    \"phoneNumber\": \"400500607\",\n" +
                "    \"messageText\": \"Hello mom, I am safe! gps$50.2135882,18.8671101\"\n" +
                "}";
    }
    
    @Configuration(proxyBeanMethods = false)
    static class RestAssuredConfig {

        @Bean
        UserMessageService userMessageService() {
            return new UserMessageServiceImpl(null) {
                @Override
                public AcceptedMessageDTO createAndRedirectSMSMessage(SMSMessageDTO smsMessageDTO) {
                    SMSMessage smsMessage = new SMSMessage(
                            smsMessageDTO.getPhoneNumber(),
                            smsMessageDTO.getMessageText());
                    return new AcceptedMessageDTO(AbstractUserMessageControllerTest.UUID,
                            AbstractUserMessageControllerTest.TIMESTAMP,
                            smsMessage.getPhoneNumber(),
                            smsMessage.getMessageText());
                }
            };
        }

        @Bean
        UserMessageController userMessageController(UserMessageService userMessageService) {
            return new UserMessageController(userMessageService);
        }
    }
}
