package br.com.mytaxi.infrastructure.input.rest.dto.acceptride;

import lombok.Builder;

@Builder
public record AcceptRideRQ(String driverId) {
}
