package br.com.mytaxi.domain.model.ride;

import br.com.mytaxi.domain.exception.DomainException;
import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.common.Constraints;
import br.com.mytaxi.domain.model.common.Coordinate;
import br.com.mytaxi.domain.model.common.Distance;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.common.Money;
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
public final class Ride {

    @EqualsAndHashCode.Include
    private final Id id;
    private final Id passengerId;
    private Id driverId;
    private final RideStatus status;
    private Money fare;
    private Distance distance;
    private Coordinate from;
    private Coordinate to;

    public static Candidate<Ride> create(String passengerId, Double latitudeFrom, Double longitudeFrom,
                                         Double latitudeTo, Double longitudeTo) {
        var passengerIdCandidate = Id.of("passengerId", passengerId);
        var fromCandidate = Coordinate.create("from", latitudeFrom, longitudeFrom);
        var toCandidate = Coordinate.create("to", latitudeTo, longitudeTo);
        var distanceCandidate = Distance.create(fromCandidate, toCandidate);
        var constraints = Constraints.builder()
                .fieldName("ride")
                .addFromCandidates(List.of(
                        passengerIdCandidate,
                        fromCandidate,
                        toCandidate,
                        distanceCandidate
                )).build();
        var candidateBuilder = Candidate.<Ride>builder().constraints(constraints);
        if (constraints.doesNotExist()) {
            candidateBuilder.value(Ride.builder()
                    .id(Id.create().getValue())
                    .passengerId(passengerIdCandidate.getValue())
                    .status(RideStatus.create().getValue())
                    .from(fromCandidate.getValue())
                    .to(toCandidate.getValue())
                    .distance(distanceCandidate.getValue())
                    .build());
        }
        return candidateBuilder.build();
    }

    public static Candidate<Ride> of(String id, String passengerId, String driverId, String status, BigDecimal fare,
                                     Double distance, Double latitudeFrom, Double longitudeFrom,
                                     Double latitudeTo, Double longitudeTo) {
        var idCandidate = Id.of("id", id);
        var passengerIdCandidate = Id.of("passengerId", passengerId);
        var statusCandidate = RideStatus.of(status);
        var fromCandidate = Coordinate.create("from", latitudeFrom, longitudeFrom);
        var toCandidate = Coordinate.create("to", latitudeTo, longitudeTo);
        Candidate<Distance> distanceCandidate = null;
        Candidate<Id> driverIdCandidate = null;
        Candidate<Money> fareCandidate = null;
        var constraintsBuilder = Constraints.builder()
                .fieldName("ride")
                .addFromCandidates(List.of(
                        passengerIdCandidate,
                        statusCandidate,
                        fromCandidate,
                        toCandidate
                ));
        if (statusCandidate.isValid()) {
            if (statusCandidate.getValue().isNotRequested()) {
                driverIdCandidate = Id.of("driverId", driverId);
                constraintsBuilder.addFromCandidates(List.of(
                        driverIdCandidate
                ));
            }
            if (statusCandidate.getValue().isCompleted()) {
                fareCandidate = Money.create("fare", fare);
                distanceCandidate = Distance.of(distance);
                constraintsBuilder.addFromCandidates(List.of(
                        fareCandidate,
                        distanceCandidate
                ));
            }
        }
        var constraints = constraintsBuilder.build();
        var candidateBuilder = Candidate.<Ride>builder().constraints(constraints);
        if (constraints.doesNotExist()) {
            candidateBuilder.value(Ride.builder()
                    .id(idCandidate.getValue())
                    .passengerId(passengerIdCandidate.getValue())
                    .driverId(driverIdCandidate != null ? driverIdCandidate.getValue() : null)
                    .status(statusCandidate.getValue())
                    .fare(fareCandidate != null ? fareCandidate.getValue() : null)
                    .from(fromCandidate.getValue())
                    .to(toCandidate.getValue())
                    .distance(distanceCandidate != null ? distanceCandidate.getValue() : null)
                    .build());
        }
        return candidateBuilder.build();
    }

    public void accept(Id driverId) {
        if (driverId == null) {
            throw new DomainException("validation.driver.must.not.be.null.to.accept.ride");
        }
        this.driverId = driverId;
        status.toAccepted();
    }

    public void start() {
        status.toInProgress();
    }

}
