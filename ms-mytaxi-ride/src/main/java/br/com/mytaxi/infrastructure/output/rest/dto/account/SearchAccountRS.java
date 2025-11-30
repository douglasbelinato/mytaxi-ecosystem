package br.com.mytaxi.infrastructure.output.rest.dto.account;

public record SearchAccountRS(String id, String name, String surname, String email,
                              boolean isPassenger, boolean isDriver) {
}
