package br.com.mytaxi.infrastructure.interfaces.output.database.repository.transaction;

import br.com.mytaxi.domain.exception.NotFoundException;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.transaction.Transaction;
import br.com.mytaxi.domain.repository.transaction.TransactionRepository;
import br.com.mytaxi.infrastructure.interfaces.output.database.mapper.transaction.TransactionDatabaseMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;

    @Override
    public Transaction save(Transaction transaction) {
        var entity = transactionJpaRepository.save(TransactionDatabaseMapper.toEntity(transaction));
        return TransactionDatabaseMapper.toDomain(entity).getValue();
    }

    @Override
    public Transaction findByIdOrThrow(Id id) {
        var entity = transactionJpaRepository.findById(id.getValue())
                .orElseThrow(() -> new NotFoundException("validation.payment.not.found"));
        return TransactionDatabaseMapper.toDomain(entity).getValue();
    }
}
