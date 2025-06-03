package br.com.mytaxi.application.usecase.dto.createaccount;

import br.com.mytaxi.application.usecase.dto.UseCaseOutputDTO;
import lombok.Builder;

@Builder
public record AccountCreatedDTO(String id) implements UseCaseOutputDTO {
}
