package br.com.mytaxi.infrastructure.interfaces.output.database.mapper.transaction;

import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.transaction.Transaction;
import br.com.mytaxi.infrastructure.interfaces.output.database.entity.transaction.TransactionEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionDatabaseMapper {

    public static TransactionEntity toEntity(Transaction transaction) {
        return TransactionEntity.builder()
                .id(transaction.getId().getValue())
                .rideId(transaction.getRideId().getValue())
                .amount(transaction.getAmount().getValue())
                .status(transaction.getStatus().getValue())
                .build();
    }

    public static Candidate<Transaction> toDomain(TransactionEntity entity) {
        return Transaction.of(entity.getId(), entity.getRideId(), entity.getAmount(), entity.getStatus());
    }

}
