package br.com.mytaxi.infrastructure.interfaces.input.rest.dto.registerposition;

import lombok.Builder;

@Builder
public record RegisterPositionRQ(Double latitude, Double longitude) {
}
