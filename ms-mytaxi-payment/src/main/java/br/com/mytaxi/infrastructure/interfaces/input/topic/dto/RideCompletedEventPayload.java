package br.com.mytaxi.infrastructure.interfaces.input.topic.dto;

import java.math.BigDecimal;

public record RideCompletedEventPayload(
    Integer version,
    String aggregateId,
    String rideId,
    BigDecimal amount,
    String creditCardToken
) {
}