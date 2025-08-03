package br.com.mytaxi.domain.model.transaction;

import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.common.Constraints;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.common.Money;
import br.com.mytaxi.domain.model.common.Text;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@ToString
public final class Transaction {

    @EqualsAndHashCode.Include
    private final Id id;
    private final Id rideId;
    private final Money amount;
    private final Text status;

    public static Candidate<Transaction> create(String rideId, BigDecimal amount) {
        var rideIdCandidate = Id.of(rideId);
        var amountCandidate = Money.create("amount", amount);
        var statusCandidate = Text.create("status", "Paid", 3, 20);
        var constraints = Constraints.builder().addFromCandidates(
                List.of(rideIdCandidate, amountCandidate, statusCandidate)
        ).build();
        var candidate = Candidate.<Transaction>builder().constraints(constraints);
        if (constraints.doesNotExist()) {
            candidate.value(Transaction.builder()
                    .id(Id.create().getValue())
                    .rideId(rideIdCandidate.getValue())
                    .amount(amountCandidate.getValue())
                    .status(statusCandidate.getValue())
                    .build());
        }
        return candidate.build();
    }

    public static Candidate<Transaction> of(String id, String rideId, BigDecimal amount, String status) {
        var idCandidate = Id.of(id);
        var rideIdCandidate = Id.of(rideId);
        var amountCandidate = Money.create("amount", amount);
        var statusCandidate = Text.create("status", status, 3, 20);
        var constraints = Constraints.builder().addFromCandidates(
                List.of(idCandidate, rideIdCandidate, amountCandidate, statusCandidate)
        ).build();
        var candidate = Candidate.<Transaction>builder().constraints(constraints);
        if (constraints.doesNotExist()) {
            candidate.value(Transaction.builder()
                    .id(idCandidate.getValue())
                    .rideId(rideIdCandidate.getValue())
                    .amount(amountCandidate.getValue())
                    .status(statusCandidate.getValue())
                    .build());
        }
        return candidate.build();
    }

}
