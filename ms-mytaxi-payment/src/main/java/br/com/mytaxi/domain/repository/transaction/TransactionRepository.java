package br.com.mytaxi.domain.repository.transaction;

import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.transaction.Transaction;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    Transaction findByIdOrThrow(Id id);

}
