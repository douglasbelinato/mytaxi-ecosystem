package br.com.mytaxi.application.usecase.dto.searchaccount;

import br.com.mytaxi.application.usecase.dto.UseCaseOutputDTO;
import lombok.Builder;

@Builder
public record AccountDTO(String id, String name, String surname, String email, String cpf, String carPlate,
                         String password, boolean isPassenger, boolean isDriver)
        implements UseCaseOutputDTO {
}
