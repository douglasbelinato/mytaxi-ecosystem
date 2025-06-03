package br.com.mytaxi.application.usecase.dto.startride;

import br.com.mytaxi.application.usecase.dto.UseCaseInputDTO;
import lombok.Builder;

@Builder
public record StartRideInputDTO(String id) implements UseCaseInputDTO {
}
