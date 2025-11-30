package br.com.mytaxi.infrastructure.input.rest.dto.registerposition;

import lombok.Builder;

@Builder
public record RegisterPositionRQ(Double latitude, Double longitude) {
}
