package br.com.mytaxi.application.usecase.dto.acceptride;

import br.com.mytaxi.application.usecase.dto.UseCaseInputDTO;
import lombok.Builder;

@Builder
public record AcceptRideInputDTO(String id, String driverId)
        implements UseCaseInputDTO {
}
