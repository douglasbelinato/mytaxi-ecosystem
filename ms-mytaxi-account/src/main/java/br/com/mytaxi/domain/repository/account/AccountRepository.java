package br.com.mytaxi.domain.repository.account;

import br.com.mytaxi.domain.model.account.Account;
import br.com.mytaxi.domain.model.common.Email;
import br.com.mytaxi.domain.model.common.Id;

import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    boolean existsByEmail(Email email);

    Optional<Account> findById(Id id);

}
