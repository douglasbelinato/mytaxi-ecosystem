package br.com.mytaxi.infrastructure.interfaces.input.rest.dto.acceptride;

import lombok.Builder;

@Builder
public record AcceptRideRQ(String driverId) {
}
