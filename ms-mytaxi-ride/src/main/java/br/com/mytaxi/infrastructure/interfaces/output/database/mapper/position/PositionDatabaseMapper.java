package br.com.mytaxi.infrastructure.interfaces.output.database.mapper.position;

import br.com.mytaxi.domain.model.position.Position;
import br.com.mytaxi.infrastructure.interfaces.output.database.entity.position.PositionEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PositionDatabaseMapper {

    public static PositionEntity toEntity(Position position) {
        return PositionEntity.builder()
                .id(position.getId().getValue())
                .rideId(position.getRideId().getValue())
                .latitude(position.getCoordinates().getLatitude())
                .longitude(position.getCoordinates().getLongitude())
                .build();
    }

    public static List<Position> toDomainList(List<PositionEntity> entities) {
        var result = new ArrayList<Position>();
        for (var entity : entities) {
            result.add(Position.of(
                    entity.getId(), entity.getRideId(), entity.getLatitude(), entity.getLongitude()).getValue());
        }
        return result;
    }

}
