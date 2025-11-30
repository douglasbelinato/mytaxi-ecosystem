package br.com.mytaxi.infrastructure.output.database.mapper.ride;

import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.ride.Ride;
import br.com.mytaxi.infrastructure.output.database.entity.ride.RideEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RideDatabaseMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "passengerId", source = "passengerId.value")
    @Mapping(target = "driverId", source = "driverId.value")
    @Mapping(target = "status", source = "status.value")
    @Mapping(target = "fare", source = "fare.value")
    @Mapping(target = "distance", source = "distance.value")
    @Mapping(target = "latitudeFrom", source = "from.latitude")
    @Mapping(target = "longitudeFrom", source = "from.longitude")
    @Mapping(target = "latitudeTo", source = "to.latitude")
    @Mapping(target = "longitudeTo", source = "to.longitude")
    @Mapping(target = "lastLatitude", source = "lastPositionRegistered.latitude")
    @Mapping(target = "lastLongitude", source = "lastPositionRegistered.longitude")
    RideEntity toEntity(Ride ride);

    default Candidate<Ride> toDomain(RideEntity entity) {
        return Ride.of(entity.getId(), entity.getPassengerId(), entity.getDriverId(), entity.getStatus(),
                entity.getFare(), entity.getDistance(), entity.getLatitudeFrom(),
                entity.getLongitudeFrom(), entity.getLatitudeTo(), entity.getLongitudeTo(),
                entity.getLastLongitude(), entity.getLastLatitude());
    }

}