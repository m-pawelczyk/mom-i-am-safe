package pro.pawelczyk.miascore.valueobjects;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 03.07.2020
 * created MessageText in pro.pawelczyk.miascore.valueobjects
 * in project mias-core
 */
public class MessageText {
    public static final String GPS_START_TAG = "gps$";
    public static final String ANONYMOUS_DOTS = "..";


    private final String messageText;
    private OptionalInt startIndexGPSData;
    private Optional<Boolean> containsGPSData;
    private Optional<String> anonymousMessage;


    public MessageText(String messageText) {
        this.messageText = messageText;
        this.startIndexGPSData = OptionalInt.empty();
        this.containsGPSData = Optional.empty();
        this.anonymousMessage = Optional.empty();
    }

    public String getMessageTextString() {
        return messageText;
    }

    public boolean containsCoordinatesData() {
        if(containsGPSData.isPresent()) {
            return containsGPSData.get();
        }

        return checkMessageContainGPSDataOrSet();
    }

    // TODO - for sur for refatoring!
    public Optional<Coordinates> extractCoordinatesIfExists() {
        if(!containsCoordinatesData()) {
            return Optional.empty();
        }

        String gpsDataString = messageText.substring(startIndexGPSData.getAsInt() + GPS_START_TAG.length(), messageText.length()).trim();
        String[] coords = gpsDataString.split(",");
        if(coords.length == 2) {
            return Optional.of(new Coordinates(
                    Double.valueOf(coords[0].toString()),
                    Double.valueOf(coords[1].toString())));
        } else if(coords.length == 3) {
            return Optional.of(new Coordinates(
                    Double.valueOf(coords[0].toString()),
                    Double.valueOf(coords[1].toString()),
                    Double.valueOf(coords[2].toString())
            ));
        } else {
            return Optional.empty();
        }
    }

    public String getAnonymousMessage() {
        if(anonymousMessage.isPresent()) {
            return anonymousMessage.get();
        }
        this.anonymousMessage = Optional.of(madeMessageAnonymous());
        return this.anonymousMessage.get();
    }

    private boolean checkMessageContainGPSDataOrSet() {
        int gpsStrIndex = messageText.toLowerCase().indexOf(GPS_START_TAG);
        if(gpsStrIndex == -1) {
            this.containsGPSData = Optional.of(Boolean.valueOf(false));
            return false;
        } else {
            this.containsGPSData = Optional.of(Boolean.valueOf(true));
            this.startIndexGPSData = OptionalInt.of(gpsStrIndex);
            return true;
        }
    }

    private String madeMessageAnonymous() {
        String msgWithoutGPSData;
        if(this.containsCoordinatesData()) {
            msgWithoutGPSData = messageText.substring(0, startIndexGPSData.getAsInt()).trim();
        } else {
            msgWithoutGPSData = messageText.trim();
        }

        return replaceContentWithDots(msgWithoutGPSData);
    }

    private String replaceContentWithDots(String msg) {
        if(msg.length() > 8) {
            return msg.substring(0, 2) + ANONYMOUS_DOTS + msg.substring(msg.length() - 2, msg.length());
        } else if(msg.length() > 4) {
            return msg.substring(0, 1) + ANONYMOUS_DOTS + msg.substring(msg.length() - 1, msg.length());
        } else {
            return ANONYMOUS_DOTS;
        }
    }
}
