package br.com.mytaxi.infrastructure.interfaces.output.gateway.mapper.account;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.infrastructure.interfaces.output.gateway.dto.account.SearchAccountRS;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountGatewayMapper {

    public static AccountDTO toAccountDTO(SearchAccountRS response) {
        return AccountDTO.builder()
                .id(response.id())
                .name(response.name())
                .surname(response.surname())
                .email(response.email())
                .isDriver(response.isDriver())
                .isPassenger(response.isPassenger())
                .build();
    }

}
