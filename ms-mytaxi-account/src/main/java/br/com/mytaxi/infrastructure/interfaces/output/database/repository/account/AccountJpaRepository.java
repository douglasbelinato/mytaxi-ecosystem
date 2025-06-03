package br.com.mytaxi.infrastructure.interfaces.output.database.repository.account;

import br.com.mytaxi.infrastructure.interfaces.output.database.entity.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {

    Optional<AccountEntity> findByEmail(String email);

}