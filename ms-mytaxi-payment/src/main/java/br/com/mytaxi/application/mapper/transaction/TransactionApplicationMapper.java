package br.com.mytaxi.application.mapper.transaction;

import br.com.mytaxi.application.usecase.dto.payride.PayRideInputDTO;
import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.transaction.Transaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionApplicationMapper {

    public static Candidate<Transaction> toDomain(PayRideInputDTO dto) {
        return Transaction.create(dto.rideId(), dto.amount(), dto.creditCardToken());
    }
}
