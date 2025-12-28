package br.com.mytaxi.application.usecase.dto.payride;

import br.com.mytaxi.application.usecase.dto.UseCaseInputDTO;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PayRideInputDTO(String rideId, BigDecimal amount, String creditCardToken) implements UseCaseInputDTO {
}
