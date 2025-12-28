package br.com.mytaxi.domain.model.event;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RideCompletedEventPayload(Integer version, String aggregateId, String rideId, BigDecimal amount,
                                        String creditCardToken)
        implements EventPayload {
}
