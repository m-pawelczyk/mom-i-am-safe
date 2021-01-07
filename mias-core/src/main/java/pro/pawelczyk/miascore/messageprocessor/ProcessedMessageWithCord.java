package pro.pawelczyk.miascore.messageprocessor;

import lombok.Getter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import pro.pawelczyk.miascore.messageprocessor.model.Position;
import pro.pawelczyk.miascore.twitterupdatter.TwitterMessage;

import java.time.Instant;
import java.util.UUID;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 02.07.2020
 * created ProcessedMessage in pro.pawelczyk.miascore.valueobjects
 * in project mias-core
 */
@Getter
public class ProcessedMessageWithCord extends ProcessedMessage {

    public static final String GOOGLE_COM_MAPS_SEARCH_API = "https://www.google.com/maps/search/?api=1&query=";
    private final Coordinates coordinates;


    public ProcessedMessageWithCord(UUID uuid, Instant timestamp, String senderId, SenderType senderType, MessageText messageText, Coordinates coordinates) {
        super(uuid, timestamp, senderId, senderType, messageText);
        this.coordinates = coordinates;
    }

    @Override
    public boolean containCoordinates() {
        return true;
    }

    public Position createPosition() {
        Position position = new Position();
        position.setLocation(new GeoJsonPoint(coordinates.getLongitude(), coordinates.getLatitude()));
        if(coordinates.is3D()) {
            position.setAltitude(coordinates.getAltitude().getAsDouble());
        }
        position.setTimestamp(getTimestamp());
        return position;
    }

    @Override
    public TwitterMessage twittMessage(String twitterAccount) {
        String msgWithLocalization = this.constructTwitterLink();
        String message = getMessageText().getMessageTextStringWithoutGPSData() + " " + msgWithLocalization;

        return new TwitterMessage(getUuid(), getTimestamp(), twitterAccount, message);
    }

    private String constructTwitterLink() {
        // https://developers.google.com/maps/documentation/urls/guide
        // https://www.google.com/maps/search/?api=1&query=47.5951518,-122.3316393
        return GOOGLE_COM_MAPS_SEARCH_API
                + coordinates.getLatitude()
                + ","
                + coordinates.getLongitude();
    }
}
