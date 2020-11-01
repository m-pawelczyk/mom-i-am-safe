package pro.pawelczyk.miascore.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MessageTextTest {

    public static final String HELLO_MOM_MESSAGE_NO_GPS = "Hello Mom! I would like to say that I am safe";
    public static final String HELLO_MOM_MESSAGE_LONG = "Hello Mom! I would like to say that I am here and I am safe! gps$50.2135882,18.8671101";
    public static final String HELLO_MOM_MESSAGE_LONG_3D = "Hello Mom! I would like to say that " +
            "I am here and I am safe! gps$50.2135882,18.8671101,357.4";

    public static final String HELLO_MOM_MESSAGE_LONG_NO_SPACE = "Hello Mom! I would like to say that I am here and I am safe!gps$50.2135882,18.8671101";
    public static final double LONGITUDE_PLUS = 50.2135882;
    public static final double LATITUDE_PLUS = 18.8671101;
    public static final double ALTITUDE_PLUS = 357.4;


    public static final String HELLO_MOM_MESSAGE_PUERTO_NATALES = "Hello Mom! I would like to say that I am in Puerto Natales! gps$-51.7293565,-72.510806";
    public static final String HELLO_MOM_MESSAGE_PUERTO_NATALES_3D = "Hello Mom! I would like to say that I am in Puerto Natales! gps$-51.7293565,-72.510806,-10.7";
    public static final double LONGITUDE_MINUS = -51.7293565;
    public static final double LATITUDE_MINUS = -72.510806;
    public static final double FAKE_ALTITUDE_MINUS= -10.7;


    @Test
    void shouldReturnTrueWhenContainsCoordinatesData() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_LONG);
        // when
        boolean containsCoordinatesData = messageText.containsCoordinatesData();
        //then
        assertThat(containsCoordinatesData).isTrue();
    }

    @Test
    void shouldReturnFalseWhenContainsCoordinatesData() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_NO_GPS);
        // when
        boolean containsCoordinatesData = messageText.containsCoordinatesData();
        //then
        assertThat(containsCoordinatesData).isFalse();
    }

    @Test
    void shouldReturnOnlyLongitudeAndLatitudeAs2DData() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_LONG);
        // when
        Optional<Coordinates> optionalCoordinates = messageText.extractCoordinatesIfExists();
        //then
        Coordinates coordinates = optionalCoordinates.get();

        assertThat(coordinates.is3D()).isFalse();
        assertThat(coordinates.getLongitude()).isEqualTo(LONGITUDE_PLUS);
        assertThat(coordinates.getLatitude()).isEqualTo(LATITUDE_PLUS);
    }

    @Test
    void shouldReturnOnlyLongitudeAndLatitudeAndAltitudeAs3DData() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_LONG_3D);
        // when
        Optional<Coordinates> optionalCoordinates = messageText.extractCoordinatesIfExists();
        //then
        Coordinates coordinates = optionalCoordinates.get();
        assertThat(coordinates.is3D()).isTrue();
        assertThat(coordinates.getLongitude()).isEqualTo(LONGITUDE_PLUS);
        assertThat(coordinates.getLatitude()).isEqualTo(LATITUDE_PLUS);
        assertThat(coordinates.getAltitude().getAsDouble()).isEqualTo(ALTITUDE_PLUS);
    }

    @Test
    void shouldReturnOnlyLongitudeAndLatitudeAs2DDataWithMinusValues() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_PUERTO_NATALES);
        // when
        Optional<Coordinates> optionalCoordinates = messageText.extractCoordinatesIfExists();
        //then
        Coordinates coordinates = optionalCoordinates.get();
        assertThat(coordinates.is3D()).isFalse();
        assertThat(coordinates.getLongitude()).isEqualTo(LONGITUDE_MINUS);
        assertThat(coordinates.getLatitude()).isEqualTo(LATITUDE_MINUS);
    }

    @Test
    void shouldReturnOnlyLongitudeAndLatitudeAndAltitudeAs3DDataWithMinusValues() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_PUERTO_NATALES_3D);
        // when
        Optional<Coordinates> optionalCoordinates = messageText.extractCoordinatesIfExists();
        //then
        Coordinates coordinates = optionalCoordinates.get();
        assertThat(coordinates.is3D()).isTrue();
        assertThat(coordinates.getLongitude()).isEqualTo(LONGITUDE_MINUS);
        assertThat(coordinates.getLatitude()).isEqualTo(LATITUDE_MINUS);
        assertThat(coordinates.getAltitude().getAsDouble()).isEqualTo(FAKE_ALTITUDE_MINUS);
    }

    @Test
    void shouldAnonymousMessage() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_LONG);
        // then
        assertThat(messageText.getAnonymousMessage()).isEqualTo("He..e!");
    }

    @Test
    void shouldAnonymousMessageWithoutSpaceBeforeGPSData() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_LONG_NO_SPACE);
        // then
        assertThat(messageText.getAnonymousMessage()).isEqualTo("He..e!");
    }
}