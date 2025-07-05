package br.com.mytaxi.infrastructure.interfaces.input.rest.dto.completeride;

import lombok.Builder;

@Builder
public record CompleteRideRQ(String creditCardToken) {
}
