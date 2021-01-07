package pro.pawelczyk.miascore.messageprocessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.pawelczyk.miascore.twitterupdatter.TwitterMessageDTO;
import pro.pawelczyk.miascore.messageprocessor.model.Position;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.pawelczyk.miascore.messageprocessor.PositionAssert.then;

class ProcessedMessageWithCordTest {

    public static final double LONGITUDE = 50.27;
    public static final double LATITUDE = 19.03;
    public static final double ALTITUDE = 300;
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
    void shouldReturnTrueWhenContainCoordinates() {
        // then
        assertThat(processedMessageWithCord.containCoordinates()).isTrue();
    }

    @Test
    void shouldReturnPositionWithCoordinatesWithoutAltitude() {
        // when
        Position position = processedMessageWithCord.createPosition();
        // then
        then(position).hasLongitudeSetTo(LONGITUDE)
                .hasLatitudeSetTo(LATITUDE)
                .hasNoAltitude();
    }

    @Test
    void shouldReturnPositionWithCoordinatesAndAltitude() {
        // given
        ProcessedMessageWithCord processedMsg = new ProcessedMessageWithCord(
                UUID.randomUUID(),
                Instant.now(),
                "500400600",
                SenderType.SMS,
                new MessageText(MESSAGE_TEXT_WITH_GPS),
                new Coordinates(LONGITUDE, LATITUDE, ALTITUDE));
        // when
        Position position = processedMsg.createPosition();
        // then
        then(position).hasLongitudeSetTo(LONGITUDE)
                .hasLatitudeSetTo(LATITUDE)
                .hasAltitudeSetTo(ALTITUDE);
    }

    @Test
    void shouldReturnMessageWithAccountAndMessageWithGPSLink() {
        // when
        TwitterMessageDTO twitterMessageDTO = processedMessageWithCord.twittMessage(KWESTIA_SZLAKU).createDTO();
        String msgWithGPS = MESSAGE_TEXT + " " + ProcessedMessageWithCord.GOOGLE_COM_MAPS_SEARCH_API + LATITUDE + "," + LONGITUDE;
        // then
        assertThat(twitterMessageDTO.getAccount()).isEqualTo(KWESTIA_SZLAKU);
        assertThat(twitterMessageDTO.getMessage()).isEqualTo(msgWithGPS);
    }
}