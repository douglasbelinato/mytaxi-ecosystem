package br.com.mytaxi.application.usecase.dto.searchride;

import br.com.mytaxi.application.usecase.dto.UseCaseInputDTO;
import lombok.Builder;

@Builder
public record SearchRideInputDTO(String id) implements UseCaseInputDTO {
}
