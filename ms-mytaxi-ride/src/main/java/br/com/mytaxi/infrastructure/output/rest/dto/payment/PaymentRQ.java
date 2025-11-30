package br.com.mytaxi.infrastructure.output.rest.dto.payment;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentRQ(String rideId, BigDecimal amount, String creditCardToken) {
}
