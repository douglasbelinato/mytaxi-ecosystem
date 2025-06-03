package br.com.mytaxi.infrastructure.interfaces.output.database.mapper.account;

import br.com.mytaxi.domain.model.account.Account;
import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.infrastructure.interfaces.output.database.entity.account.AccountEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountDatabaseMapper {

    public static AccountEntity toEntity(Account account) {
        return AccountEntity.builder()
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

    public static Candidate<Account> toDomain(AccountEntity entity) {
        return Account.of(entity.getId(), entity.getName(), entity.getSurname(), entity.getEmail(),
                entity.getCpf(), entity.getCarPlate(), entity.getPassword(),
                entity.isPassenger(), entity.isDriver());
    }

}
