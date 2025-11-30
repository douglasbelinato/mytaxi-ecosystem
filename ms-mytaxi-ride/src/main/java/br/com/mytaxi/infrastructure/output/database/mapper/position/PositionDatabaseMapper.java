package br.com.mytaxi.infrastructure.output.database.mapper.position;

import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.position.Position;
import br.com.mytaxi.infrastructure.output.database.entity.position.PositionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface PositionDatabaseMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "rideId", source = "rideId.value")
    @Mapping(target = "latitude", source = "coordinates.latitude")
    @Mapping(target = "longitude", source = "coordinates.longitude")
    PositionEntity toEntity(Position position);

    default Position toDomain(PositionEntity entity) {
        return Position.of(entity.getId(), entity.getRideId(), entity.getLatitude(), entity.getLongitude()).getValue();
    }

    default List<Position> toDomainList(List<PositionEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .toList();
    }

}