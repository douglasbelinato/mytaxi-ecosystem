package br.com.mytaxi.infrastructure.interfaces.input.rest.dto.createaccount;

import lombok.Builder;

@Builder
public record CreateAccountRQ(String name, String surname, String email, String cpf, String carPlate, String password,
                              boolean isPassenger, boolean isDriver) {
}
