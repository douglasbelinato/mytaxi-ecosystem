package br.com.mytaxi.application.usecase.dto.registerposition;

import br.com.mytaxi.application.usecase.dto.UseCaseInputDTO;
import lombok.Builder;

@Builder
public record RegisterPositionInputDTO(String rideId, Double latitude, Double longitude)
        implements UseCaseInputDTO {
}
