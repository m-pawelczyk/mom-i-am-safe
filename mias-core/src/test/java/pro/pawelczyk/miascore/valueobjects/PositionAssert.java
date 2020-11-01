package pro.pawelczyk.miascore.valueobjects;

import pro.pawelczyk.miascore.model.Position;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PositionAssert {
    private final Position position;

    public PositionAssert(Position position) {
        this.position = position;
    }

    static PositionAssert then(Position position) {
        return new PositionAssert(position);
    }

    PositionAssert hasLongitudeSetTo(Double longitude) {
        assertThat(position.getLocation().getCoordinates().get(0)).isEqualTo(longitude);
        return this;
    }

    PositionAssert hasLatitudeSetTo(Double latitude) {
        assertThat(position.getLocation().getCoordinates().get(1)).isEqualTo(latitude);
        return this;
    }

    PositionAssert hasNoAltitude() {
        assertThat(position.getAltitude()).isNull();
        return this;
    }

    PositionAssert hasAltitudeSetTo(Double altitude) {
        assertThat(position.getAltitude()).isEqualTo(altitude);
        return this;
    }
}
