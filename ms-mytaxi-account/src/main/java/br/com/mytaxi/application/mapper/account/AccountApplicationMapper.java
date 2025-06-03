package br.com.mytaxi.application.mapper.account;

import br.com.mytaxi.application.usecase.dto.createaccount.AccountCreatedDTO;
import br.com.mytaxi.application.usecase.dto.createaccount.CreateAccountDTO;
import br.com.mytaxi.application.usecase.dto.searchaccount.AccountDTO;
import br.com.mytaxi.domain.model.account.Account;
import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.common.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountApplicationMapper {

    public static Candidate<Account> toDomain(CreateAccountDTO dto) {
        return Account.create(dto.name(), dto.surname(), dto.email(), dto.cpf(), dto.carPlate(), dto.password(),
                dto.isPassenger(), dto.isDriver());
    }

    public static AccountCreatedDTO toAccountCreatedDTO(Id id) {
        return AccountCreatedDTO.builder().id(id.getValue()).build();
    }

    public static AccountDTO toAccountDTO(Account account) {
        return AccountDTO.builder()
                .id(account.getId().getValue())
                .name(account.getName().getValue())
                .surname(account.getSurname().getValue())
                .email(account.getEmail().getValue())
                .cpf(account.getCpf().getValue())
                .carPlate(account.getCarPlate() != null ? account.getCarPlate().getValue() : null)
                .password(account.getPassword().getValue())
                .isPassenger(account.isPassenger())
                .isDriver(account.isDriver())
                .build();
    }

}
