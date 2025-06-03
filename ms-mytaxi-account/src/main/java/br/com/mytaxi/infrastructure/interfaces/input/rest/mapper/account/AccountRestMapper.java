package br.com.mytaxi.infrastructure.interfaces.input.rest.mapper.account;

import br.com.mytaxi.application.usecase.dto.createaccount.AccountCreatedDTO;
import br.com.mytaxi.application.usecase.dto.createaccount.CreateAccountDTO;
import br.com.mytaxi.application.usecase.dto.searchaccount.AccountDTO;
import br.com.mytaxi.application.usecase.dto.searchaccount.SearchAccountDTO;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.createaccount.AccountCreatedRS;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.createaccount.CreateAccountRQ;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.searchaccount.AccountRS;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountRestMapper {

    public static CreateAccountDTO toCreateAccountDTO(CreateAccountRQ request) {
        return CreateAccountDTO.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .cpf(request.cpf())
                .carPlate(request.carPlate())
                .password(request.password())
                .isPassenger(request.isPassenger())
                .isDriver(request.isDriver())
                .build();
    }

    public static AccountCreatedRS toAccountCreatedRS(AccountCreatedDTO dto) {
        return AccountCreatedRS.builder()
                .id(dto.id())
                .build();
    }

    public static SearchAccountDTO toSearchAccountDTO(String id) {
        return SearchAccountDTO.builder().id(id).build();
    }

    public static AccountRS toAccountRS(AccountDTO dto) {
        return AccountRS.builder()
                .id(dto.id())
                .name(dto.name())
                .surname(dto.surname())
                .email(dto.email())
                .cpf(dto.cpf())
                .carPlate(dto.carPlate())
                .isPassenger(dto.isPassenger())
                .isDriver(dto.isDriver())
                .build();
    }

}
