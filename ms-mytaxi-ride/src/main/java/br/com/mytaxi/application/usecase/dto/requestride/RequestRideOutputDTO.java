package br.com.mytaxi.application.usecase.dto.requestride;

import br.com.mytaxi.application.usecase.dto.UseCaseOutputDTO;
import lombok.Builder;

@Builder
public record RequestRideOutputDTO(String id) implements UseCaseOutputDTO {
}
