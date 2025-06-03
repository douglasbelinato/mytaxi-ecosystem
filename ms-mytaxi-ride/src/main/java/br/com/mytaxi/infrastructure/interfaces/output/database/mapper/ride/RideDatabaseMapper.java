package br.com.mytaxi.infrastructure.interfaces.output.database.mapper.ride;

import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.ride.Ride;
import br.com.mytaxi.infrastructure.interfaces.output.database.entity.ride.RideEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideDatabaseMapper {

    public static RideEntity toEntity(Ride ride) {
        return RideEntity.builder()
                .id(ride.getId().getValue())
                .passengerId(ride.getPassengerId().getValue())
                .driverId(ride.getDriverId() != null ? ride.getDriverId().getValue() : null)
                .status(ride.getStatus().getValue().name())
                .fare(ride.getFare() != null ? ride.getFare().getValue() : null)
                .distance(ride.getDistance() != null ? ride.getDistance().getValue() : null)
                .latitudeFrom(ride.getFrom() != null ? ride.getFrom().getLatitude() : null)
                .longitudeFrom(ride.getFrom() != null ? ride.getFrom().getLongitude() : null)
                .latitudeTo(ride.getTo() != null ? ride.getTo().getLatitude() : null)
                .longitudeTo(ride.getTo() != null ? ride.getTo().getLongitude() : null)
                .build();
    }

    public static Candidate<Ride> toDomain(RideEntity entity) {
        return Ride.of(entity.getId(), entity.getPassengerId(), entity.getDriverId(), entity.getStatus(),
                entity.getFare(), entity.getDistance(), entity.getLatitudeFrom(),
                entity.getLongitudeFrom(), entity.getLatitudeTo(), entity.getLongitudeTo());
    }

}
