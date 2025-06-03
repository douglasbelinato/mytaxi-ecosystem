package br.com.mytaxi.domain.model.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.ObjectUtils;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@ToString
public final class Coordinate {

    private final Double latitude;
    private final Double longitude;

    public static Candidate<Coordinate> create(String fieldName, Double latitude, Double longitude) {
        var finalFieldName = ObjectUtils.defaultIfNull(fieldName, "undefined");
        var candidateBuilder = Candidate.<Coordinate>builder();
        var constraintsBuilder = Constraints.builder().fieldName(finalFieldName);
        if (isAnInvalidateLatitude(latitude) || isAnInvalidateLongitude(longitude)) {
            constraintsBuilder.add(finalFieldName, "latitude must be between -90 and 90 degrees and longitude must be between -180 and 180 degrees");
        } else {
            candidateBuilder.value(Coordinate.builder().latitude(latitude).longitude(longitude).build());
        }
        return candidateBuilder.constraints(constraintsBuilder.build()).build();
    }

    private static boolean isAnInvalidateLatitude(Double latitude) {
        return latitude == null || latitude < -90 || latitude > 90;
    }

    private static boolean isAnInvalidateLongitude(Double longitude) {
        return longitude == null || longitude < -180 || longitude > 180;
    }

}
