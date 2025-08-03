package br.com.mytaxi.infrastructure.interfaces.input.rest.dto.payride;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PayRideRQ(String rideId, BigDecimal amount) {
}
