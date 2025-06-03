package br.com.mytaxi.infrastructure.interfaces.input.rest.dto.requestride;

import lombok.Builder;

@Builder
public record RequestRideRQ(String passengerId, Double latitudeFrom, Double longitudeFrom,
                            Double latitudeTo, Double longitudeTo) {
}
