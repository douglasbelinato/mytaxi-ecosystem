package br.com.mytaxi.infrastructure.interfaces.output.database.repository.transaction;

import br.com.mytaxi.infrastructure.interfaces.output.database.entity.transaction.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, String> {
}
