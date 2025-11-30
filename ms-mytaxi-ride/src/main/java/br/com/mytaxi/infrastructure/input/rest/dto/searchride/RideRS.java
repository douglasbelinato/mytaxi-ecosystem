package br.com.mytaxi.infrastructure.input.rest.dto.searchride;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RideRS(String id, String passengerId, String passengerName, String passengerSurname,
                     String passengerEmail, String driverId, String driverName, String driverSurname,
                     String driverEmail, String status, BigDecimal fare,
                     Double distance, Double latitudeFrom, Double longitudeFrom,
                     Double latitudeTo, Double longitudeTo) {
}
