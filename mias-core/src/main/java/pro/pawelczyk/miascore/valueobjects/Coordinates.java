package pro.pawelczyk.miascore.valueobjects;

import lombok.Getter;

import java.util.OptionalDouble;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 02.07.2020
 * created Coordinate2D in pro.pawelczyk.miascore.valueobjects
 * in project mias-core
 */
@Getter
public class Coordinates {

    public static final double DEATH_SEA_ALTITUDE_WITH_MARGIN = -450d;
    public static final double MOUNT_EVEREST_ALTITUDE = 8848d;
    public static final double MIN_LONGITUDE = -180d;
    public static final double MAX_LONGITUDE = 180d;
    public static final double MIN_LATITUDE = -180d;
    public static final double MAX_LATITUDE = 180d;

    private final double longitude;
    private final double latitude;
    private final OptionalDouble altitude;

    public Coordinates(double longitude, double latitude, double  altitude) {
        validate2DCoordinates(longitude, latitude);

        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = emptyIfNotValid(altitude);
    }

    public Coordinates(double longitude, double latitude) {
        validate2DCoordinates(longitude, latitude);

        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = OptionalDouble.empty();
    }

    public boolean is3D() {
        return (altitude.isPresent()) ? true : false;
    }

    private void validate2DCoordinates(double longitude, double latitude) {
        if(longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            throw new IllegalArgumentException("Longitude should be in range <-180;180>. I is out range: " + longitude);
        }

        if(longitude < MIN_LATITUDE || longitude > MAX_LATITUDE) {
            throw new IllegalArgumentException("Latitude should be in range <-90;90>. I is out range: " + latitude);
        }
    }

    private OptionalDouble emptyIfNotValid(double  altitude) {
        if(altitude < DEATH_SEA_ALTITUDE_WITH_MARGIN || latitude > MOUNT_EVEREST_ALTITUDE) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(altitude);
    }
}
