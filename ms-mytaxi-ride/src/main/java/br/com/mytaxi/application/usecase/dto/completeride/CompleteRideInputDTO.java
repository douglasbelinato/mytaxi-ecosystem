package br.com.mytaxi.application.usecase.dto.completeride;

import br.com.mytaxi.application.usecase.dto.UseCaseInputDTO;
import lombok.Builder;

@Builder
public record CompleteRideInputDTO(String id, String creditCardToken) implements UseCaseInputDTO {
}
