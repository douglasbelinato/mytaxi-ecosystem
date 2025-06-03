package br.com.mytaxi.infrastructure.interfaces.input.rest.dto.searchaccount;

import lombok.Builder;

@Builder
public record AccountRS(String id, String name, String surname, String email, String cpf, String carPlate,
                        boolean isPassenger, boolean isDriver) {
}
