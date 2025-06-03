package br.com.mytaxi.domain.model.ride;

import br.com.mytaxi.domain.exception.DomainException;
import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.common.Constraints;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@ToString
public final class RideStatus {

    private RideStatusEnum value;

    public static Candidate<RideStatus> create() {
        return Candidate.<RideStatus>builder()
                .value(RideStatus.builder().value(RideStatusEnum.REQUESTED).build())
                .build();
    }

    public static Candidate<RideStatus> of(String value) {
        var constraintsBuilder = Constraints.builder();
        var candidateBuilder = Candidate.<RideStatus>builder();
        var optionalRideStatusEnum = RideStatusEnum.of(value);
        if (optionalRideStatusEnum.isEmpty()) {
            constraintsBuilder.add("status", "validation.ride.status.invalid");
        } else {
            candidateBuilder.value(RideStatus.builder().value(optionalRideStatusEnum.get()).build());
        }
        return candidateBuilder.constraints(constraintsBuilder.build()).build();
    }

    public boolean isNotRequested() {
        return value != RideStatusEnum.REQUESTED;
    }

    public boolean isNotInProgress() {
        return value != RideStatusEnum.IN_PROGRESS;
    }

    public boolean isNotAccepted() {
        return value != RideStatusEnum.ACCEPTED;
    }

    public boolean isCompleted() {
        return value == RideStatusEnum.COMPLETED;
    }

    public void toAccepted() {
        if (isNotRequested()) {
            throw new DomainException("validation.invalid.ride.status.to.be.accepted");
        }
        value = RideStatusEnum.ACCEPTED;
    }

    public void toInProgress() {
        if (isNotAccepted()) {
            throw new DomainException("validation.invalid.ride.status.to.be.in.progress");
        }
        value = RideStatusEnum.IN_PROGRESS;
    }

}
