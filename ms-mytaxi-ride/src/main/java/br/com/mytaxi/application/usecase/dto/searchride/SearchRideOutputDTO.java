package br.com.mytaxi.application.usecase.dto.searchride;

import br.com.mytaxi.application.usecase.dto.UseCaseOutputDTO;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SearchRideOutputDTO(String id, String passengerId, String passengerName, String passengerSurname,
                                  String passengerEmail, String driverId, String driverName, String driverSurname,
                                  String driverEmail, String status, BigDecimal fare, Double distance,
                                  Double latitudeFrom, Double longitudeFrom, Double latitudeTo, Double longitudeTo)
        implements UseCaseOutputDTO {
}
