package br.com.mytaxi.infrastructure.interfaces.output.database.repository.account;

import br.com.mytaxi.domain.model.account.Account;
import br.com.mytaxi.domain.model.common.Email;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.repository.account.AccountRepository;
import br.com.mytaxi.infrastructure.interfaces.output.database.entity.account.AccountEntity;
import br.com.mytaxi.infrastructure.interfaces.output.database.mapper.account.AccountDatabaseMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Named
@RequiredArgsConstructor
class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account save(Account account) {
        var entity = accountJpaRepository.save(AccountDatabaseMapper.toEntity(account));
        return mapDomainFromEntity(entity);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return accountJpaRepository.findByEmail(email.getValue()).isPresent();
    }

    @Override
    public Optional<Account> findById(Id id) {
        var accountEntityOptional = accountJpaRepository.findById(id.getValue());
        return accountEntityOptional.map(this::mapDomainFromEntity);
    }

    private Account mapDomainFromEntity(AccountEntity entity) {
        var candidate = AccountDatabaseMapper.toDomain(entity);
        candidate.validate();
        return candidate.getValue();
    }

}
