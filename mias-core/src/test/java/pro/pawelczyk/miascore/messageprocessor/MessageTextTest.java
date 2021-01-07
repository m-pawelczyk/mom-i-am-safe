package pro.pawelczyk.miascore.messageprocessor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pro.pawelczyk.miascore.messageprocessor.Coordinates;
import pro.pawelczyk.miascore.messageprocessor.MessageText;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTextTest {

    public static final String HELLO_MOM_MESSAGE_NO_GPS = "Hello Mom! I would like to say that I am safe";
    public static final String HELLO_MOM_MESSAGE_LONG = "Hello Mom! I would like to say that I am here and I am safe! gps$50.2135882,18.8671101";
    public static final String HELLO_MOM_MESSAGE_LONG_NO_SPACE = "Hello Mom! I would like to say that I am here and I am safe!gps$50.2135882,18.8671101";
    
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

    @ParameterizedTest(name = "should return longitude: {1}, latitude: {2} for message text: {0}")
    @CsvFileSource(resources = "/messages2D_testdata.csv", numLinesToSkip = 1, delimiterString = ";")
    void shouldVerify2DGPSDataAgainstMessagesPreparedInFile(String message, double longitude, double latitude) {
        // given
        MessageText messageText = new MessageText(message);
        // when
        Optional<Coordinates> optionalCoordinates = messageText.extractCoordinatesIfExists();
        //then
        Coordinates coordinates = optionalCoordinates.get();
        assertThat(coordinates.is3D()).isFalse();
        assertThat(coordinates.getLongitude()).isEqualTo(longitude);
        assertThat(coordinates.getLatitude()).isEqualTo(latitude);
    }


    @ParameterizedTest(name = "should return longitude: {1}, latitude: {2}, and altitude {3} for message text: {0}")
    @CsvFileSource(resources = "/messages3D_testdata.csv", numLinesToSkip = 1, delimiterString = ";")
    void shouldVerify3DGPSDataAgainstMessagesPreparedInFile(String message, Double longitude, Double latitude, Double altitude) {
        // given
        MessageText messageText = new MessageText(message);
        // when
        Optional<Coordinates> optionalCoordinates = messageText.extractCoordinatesIfExists();
        //then
        Coordinates coordinates = optionalCoordinates.get();
        assertThat(coordinates.is3D()).isTrue();
        assertThat(coordinates.getLongitude()).isEqualTo(longitude);
        assertThat(coordinates.getLatitude()).isEqualTo(latitude);
        assertThat(coordinates.getAltitude().getAsDouble()).isEqualTo(altitude);
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