package br.com.mytaxi.infrastructure.input.rest.dto.completeride;

import lombok.Builder;

@Builder
public record CompleteRideRQ(String creditCardToken) {
}
