package pro.pawelczyk.miascore.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Optional;

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
    void containsCoordinatesData() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_LONG);
        // when
        boolean containsCoordinatesData = messageText.containsCoordinatesData();
        //then
        assertTrue(containsCoordinatesData);
    }

    @Test
    void notContainsCoordinatesData() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_NO_GPS);
        // when
        boolean containsCoordinatesData = messageText.containsCoordinatesData();
        //then
        assertFalse(containsCoordinatesData);
    }

    @Test
    void extractCoordinatesIfExists2D() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_LONG);
        // when
        Optional<Coordinates> optionalCoordinates = messageText.extractCoordinatesIfExists();
        //then
        Coordinates coordinates = optionalCoordinates.get();
        assertFalse(coordinates.is3D());
        assertEquals(LONGITUDE_PLUS, coordinates.getLongitude());
        assertEquals(LATITUDE_PLUS, coordinates.getLatitude());
    }

    @Test
    void extractCoordinatesIfExists3D() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_LONG_3D);
        // when
        Optional<Coordinates> optionalCoordinates = messageText.extractCoordinatesIfExists();
        //then
        Coordinates coordinates = optionalCoordinates.get();
        assertTrue(coordinates.is3D());
        assertEquals(LONGITUDE_PLUS, coordinates.getLongitude());
        assertEquals(LATITUDE_PLUS, coordinates.getLatitude());
        assertEquals(ALTITUDE_PLUS, coordinates.getAltitude().getAsDouble());
    }

    @Test
    void extractCoordinatesIfExists2DWithMinusValues() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_PUERTO_NATALES);
        // when
        Optional<Coordinates> optionalCoordinates = messageText.extractCoordinatesIfExists();
        //then
        Coordinates coordinates = optionalCoordinates.get();
        assertFalse(coordinates.is3D());
        assertEquals(LONGITUDE_MINUS, coordinates.getLongitude());
        assertEquals(LATITUDE_MINUS, coordinates.getLatitude());
    }

    @Test
    void extractCoordinatesIfExists3DWithMinusValues() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_PUERTO_NATALES_3D);
        // when
        Optional<Coordinates> optionalCoordinates = messageText.extractCoordinatesIfExists();
        //then
        Coordinates coordinates = optionalCoordinates.get();
        assertTrue(coordinates.is3D());
        assertEquals(LONGITUDE_MINUS, coordinates.getLongitude());
        assertEquals(LATITUDE_MINUS, coordinates.getLatitude());
        assertEquals(FAKE_ALTITUDE_MINUS, coordinates.getAltitude().getAsDouble());
    }

    @Test
    void getAnonymousMessage() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_LONG);
        // then
        assertEquals("He..e!", messageText.getAnonymousMessage());
    }

    @Test
    void getAnonymousMessageNoSpaceBeforeGPSData() {
        // given
        MessageText messageText = new MessageText(HELLO_MOM_MESSAGE_LONG_NO_SPACE);
        // then
        assertEquals("He..e!", messageText.getAnonymousMessage());
    }
}