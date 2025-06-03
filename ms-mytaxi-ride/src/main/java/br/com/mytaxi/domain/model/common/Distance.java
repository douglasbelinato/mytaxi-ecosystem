package br.com.mytaxi.domain.model.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.MathContext;
import java.math.RoundingMode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@ToString
public final class Distance {

    private static final MathContext MATH_CONTEXT = new MathContext(15, RoundingMode.HALF_UP);

    private final Double value;

    public static Candidate<Distance> create(Candidate<Coordinate> candidateFrom, Candidate<Coordinate> candidateTo) {
        var fieldName = "distance";
        var candidateBuilder = Candidate.<Distance>builder();
        var constraints = Constraints.builder().fieldName(fieldName);
        if (candidateFrom == null || candidateTo == null) {
            constraints.add(fieldName, "from and to coordinates must not be null");
        } else if (candidateFrom.isNotValid() || candidateTo.isNotValid()) {
            constraints.add(fieldName, "from and to coordinates must be valid");
        } else {
            candidateBuilder.value(Distance.builder().
                    value(calculateHaversineFormula(candidateFrom.getValue(), candidateTo.getValue()))
                    .build());
        }
        return candidateBuilder.constraints(constraints.build()).build();
    }

    public static Candidate<Distance> of(Double distance) {
        var fieldName = "distance";
        var candidateBuilder = Candidate.<Distance>builder();
        var constraints = Constraints.builder().fieldName(fieldName);
        if (distance == null) {
            constraints.add(fieldName, "must not be null");
        } else {
            candidateBuilder.value(Distance.builder().
                    value(distance)
                    .build());
        }
        return candidateBuilder.constraints(constraints.build()).build();
    }

    private static Double calculateHaversineFormula(Coordinate from, Coordinate to) {
        var earthRadiusInKm = 6371.0088;
        var latitudeFrom = from.getLatitude();
        var longitudeFrom = from.getLongitude();
        var latitudeTo = to.getLatitude();
        var longitudeTo = to.getLongitude();
        double latRad1 = Math.toRadians(latitudeFrom);
        double latRad2 = Math.toRadians(latitudeTo);
        double deltaLat = Math.toRadians(latitudeTo - latitudeFrom);
        double deltaLon = Math.toRadians(longitudeTo - longitudeFrom);
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(latRad1) * Math.cos(latRad2)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadiusInKm * c;
    }

}
