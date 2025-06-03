package br.com.mytaxi.application.usecase.dto.searchaccount;

import br.com.mytaxi.application.usecase.dto.UseCaseInputDTO;
import lombok.Builder;

@Builder
public record SearchAccountDTO(String id) implements UseCaseInputDTO {
}
