package pro.pawelczyk.miascore.valueobjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.pawelczyk.miascore.messages.TwitterMessageDTO;
import pro.pawelczyk.miascore.model.Position;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProcessedMessageWithCordTest {

    public static final double LONGITUDE = 50.27;
    public static final double LATITUDE = 19.03;
    public static final String MESSAGE_TEXT = "Ala ma kota";
    public static final String MESSAGE_TEXT_WITH_GPS = "Ala ma kota gps$50.2135882,18.8671101";
    public static final String KWESTIA_SZLAKU = "kwetiaszlaku";
    ProcessedMessageWithCord processedMessageWithCord;

    @BeforeEach
    void setUp() {
        processedMessageWithCord = new ProcessedMessageWithCord(
                UUID.randomUUID(),
                Instant.now(),
                "500400600",
                SenderType.SMS,
                new MessageText(MESSAGE_TEXT_WITH_GPS),
                new Coordinates(LONGITUDE, LATITUDE));
    }

    @Test
    void containCoordinates() {
        // then
        assertTrue(processedMessageWithCord.containCoordinates());
    }

    @Test
    void createPositionWithoutAltitude() {
        // when
        Position position = processedMessageWithCord.createPosition();
        // then
        assertEquals(LONGITUDE, position.getLongitude());
        assertEquals(LATITUDE, position.getLatitude());
        assertEquals(0.0, position.getAltitude());
    }

    @Test
    void createPositionWithAltitude() {
        // given
        ProcessedMessageWithCord processedMsg = new ProcessedMessageWithCord(
                UUID.randomUUID(),
                Instant.now(),
                "500400600",
                SenderType.SMS,
                new MessageText(MESSAGE_TEXT_WITH_GPS),
                new Coordinates(LONGITUDE, LATITUDE, 300));
        // when
        Position position = processedMsg.createPosition();
        // then
        assertEquals(LONGITUDE, position.getLongitude());
        assertEquals(LATITUDE, position.getLatitude());
        assertEquals(300, position.getAltitude());
    }

    @Test
    void twittMessage() {
        // when
        TwitterMessageDTO twitterMessageDTO = processedMessageWithCord.twittMessage("kwetiaszlaku").createDTO();
        String msgWithGPS = MESSAGE_TEXT + " " + ProcessedMessageWithCord.GOOGLE_COM_MAPS_SEARCH_API + LATITUDE + "," + LONGITUDE;
        // then
        assertEquals(KWESTIA_SZLAKU, twitterMessageDTO.getAccount());
        assertEquals(msgWithGPS, twitterMessageDTO.getMessage());
    }
}