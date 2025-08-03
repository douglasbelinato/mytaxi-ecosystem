package br.com.mytaxi.application.output.rest.dto.account;

import lombok.Builder;

@Builder
public record AccountDTO(String id, String name, String surname, String email, boolean isPassenger, boolean isDriver) {
}
