package br.com.mytaxi.infrastructure.interfaces.output.database.mapper.position;

import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.position.Position;
import br.com.mytaxi.infrastructure.interfaces.output.database.entity.position.PositionEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PositionDatabaseMapper {

    public static PositionEntity toEntity(Position position) {
        return PositionEntity.builder()
                .id(position.getId().getValue())
                .rideId(position.getRideId().getValue())
                .latitude(position.getCoordinate().getLatitude())
                .longitude(position.getCoordinate().getLongitude())
                .build();
    }

    public static Candidate<Position> toDomain(PositionEntity entity) {
        return Position.of(entity.getId(), entity.getRideId(), entity.getLatitude(), entity.getLongitude());
    }

}
