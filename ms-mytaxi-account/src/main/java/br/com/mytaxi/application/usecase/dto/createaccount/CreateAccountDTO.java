package br.com.mytaxi.application.usecase.dto.createaccount;

import br.com.mytaxi.application.usecase.dto.UseCaseInputDTO;
import lombok.Builder;

@Builder
public record CreateAccountDTO(String name, String surname, String email, String cpf, String carPlate, String password,
                               boolean isPassenger, boolean isDriver)
        implements UseCaseInputDTO {
}
