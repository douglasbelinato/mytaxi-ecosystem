package br.com.mytaxi.infrastructure.interfaces.input.rest.dto.searchride;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RideRS(String id, String passengerId, String driverId, String status, BigDecimal fare,
                     Double distance, Double latitudeFrom, Double longitudeFrom,
                     Double latitudeTo, Double longitudeTo) {
}
