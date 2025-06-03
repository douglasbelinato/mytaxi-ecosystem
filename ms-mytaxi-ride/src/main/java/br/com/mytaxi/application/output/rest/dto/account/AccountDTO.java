package br.com.mytaxi.application.output.rest.dto.account;

import lombok.Builder;

@Builder
public record AccountDTO(String id, boolean isPassenger, boolean isDriver) {
}
