package br.com.mytaxi.domain.model.position;

import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.common.Constraints;
import br.com.mytaxi.domain.model.common.Coordinate;
import br.com.mytaxi.domain.model.common.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@ToString
public final class Position {

    @EqualsAndHashCode.Include
    private final Id id;
    private final Id rideId;
    private final Coordinate coordinate;

    public static Candidate<Position> create(String rideId, Double latitude, Double longitude) {
        var rideIdCandidate = Id.of("rideId", rideId);
        var coordinateCandidate = Coordinate.create("coordinate", latitude, longitude);
        var constraints = Constraints.builder()
                .fieldName("position")
                .addFromCandidates(
                        List.of(rideIdCandidate, coordinateCandidate))
                .build();
        var candidateBuilder = Candidate.<Position>builder().constraints(constraints);
        if (constraints.doesNotExist()) {
            candidateBuilder.value(Position.builder()
                    .id(Id.create().getValue())
                    .rideId(rideIdCandidate.getValue())
                    .coordinate(coordinateCandidate.getValue())
                    .build());
        }
        return candidateBuilder.build();
    }

    public static Candidate<Position> of(String id, String rideId, Double latitude, Double longitude) {
        var idCandidate = Id.of("id", id);
        var rideIdCandidate = Id.of("rideId", rideId);
        var coordinateCandidate = Coordinate.create("coordinate", latitude, longitude);
        var constraints = Constraints.builder()
                .fieldName("position")
                .addFromCandidates(
                        List.of(idCandidate, rideIdCandidate, coordinateCandidate))
                .build();
        var candidateBuilder = Candidate.<Position>builder().constraints(constraints);
        if (constraints.doesNotExist()) {
            candidateBuilder.value(Position.builder()
                    .id(idCandidate.getValue())
                    .rideId(rideIdCandidate.getValue())
                    .coordinate(coordinateCandidate.getValue())
                    .build());
        }
        return candidateBuilder.build();
    }

}
