package br.com.mytaxi.domain.model.ride;

import br.com.mytaxi.domain.exception.DomainException;
import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.common.Constraints;
import br.com.mytaxi.domain.model.common.Coordinates;
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
    private Coordinates from;
    private Coordinates to;
    private Coordinates lastPositionRegistered;

    public static Candidate<Ride> create(String passengerId, Double latitudeFrom, Double longitudeFrom,
                                         Double latitudeTo, Double longitudeTo) {
        var passengerIdCandidate = Id.of("passengerId", passengerId);
        var fromCandidate = Coordinates.create("from", latitudeFrom, longitudeFrom);
        var toCandidate = Coordinates.create("to", latitudeTo, longitudeTo);
        var constraints = Constraints.builder()
                .fieldName("ride")
                .addFromCandidates(List.of(
                        passengerIdCandidate,
                        fromCandidate,
                        toCandidate
                )).build();
        var candidateBuilder = Candidate.<Ride>builder().constraints(constraints);
        if (constraints.doesNotExist()) {
            candidateBuilder.value(Ride.builder()
                    .id(Id.create().getValue())
                    .passengerId(passengerIdCandidate.getValue())
                    .status(RideStatus.create().getValue())
                    .from(fromCandidate.getValue())
                    .to(toCandidate.getValue())
                    .lastPositionRegistered(fromCandidate.getValue())
                    .distance(Distance.of(0d).getValue())
                    .build());
        }
        return candidateBuilder.build();
    }

    public static Candidate<Ride> of(String id, String passengerId, String driverId, String status, BigDecimal fare,
                                     Double distance, Double latitudeFrom, Double longitudeFrom,
                                     Double latitudeTo, Double longitudeTo, Double lastLongitude,
                                     Double lastLatitude) {
        var idCandidate = Id.of("id", id);
        var passengerIdCandidate = Id.of("passengerId", passengerId);
        var statusCandidate = RideStatus.of(status);
        var fromCandidate = Coordinates.create("from", latitudeFrom, longitudeFrom);
        var toCandidate = Coordinates.create("to", latitudeTo, longitudeTo);
        var lastPositionRegisteredCandidate = Coordinates.create("lastPositionRegistered",
                lastLatitude, lastLongitude);
        var distanceCandidate = Distance.of(distance);
        Candidate<Id> driverIdCandidate = null;
        Candidate<Money> fareCandidate = null;
        var constraintsBuilder = Constraints.builder()
                .fieldName("ride")
                .addFromCandidates(List.of(
                        passengerIdCandidate,
                        statusCandidate,
                        fromCandidate,
                        toCandidate,
                        lastPositionRegisteredCandidate,
                        distanceCandidate
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
                constraintsBuilder.addFromCandidates(List.of(
                        fareCandidate
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
                    .lastPositionRegistered(lastPositionRegisteredCandidate.getValue())
                    .distance(distanceCandidate.getValue())
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

    public void updatePosition(Double latitude, Double longitude) {
        var newPosition = Coordinates.create("lastPositionRegistered", latitude, longitude).getValue();
        var distanceToBeAdded = Distance.create(lastPositionRegistered, newPosition).getValue();
        this.distance = this.distance.add(distanceToBeAdded);
        this.lastPositionRegistered = newPosition;
    }

    public void complete(Distance totalDistance) {
        status.toCompleted();
        distance = totalDistance;
        fare = Money.create("fare",
                new BigDecimal("2.1").multiply(BigDecimal.valueOf(totalDistance.getValue()))).getValue();
    }

//    public String getId() {
//        return id.getValue();
//    }

}
