package br.com.mytaxi.application.mapper.ride;

import br.com.mytaxi.application.usecase.dto.requestride.RequestRideInputDTO;
import br.com.mytaxi.application.usecase.dto.requestride.RequestRideOutputDTO;
import br.com.mytaxi.application.usecase.dto.searchride.SearchRideOutputDTO;
import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.ride.Ride;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideApplicationMapper {

    public static Candidate<Ride> toDomain(RequestRideInputDTO dto) {
        return Ride.create(dto.passengerId(), dto.latitudeFrom(), dto.longitudeFrom(),
                dto.latitudeTo(), dto.longitudeTo());
    }

    public static RequestRideOutputDTO toRequestRideOutputDTO(Ride ride) {
        return RequestRideOutputDTO.builder().id(ride.getId().getValue()).build();
    }

    public static SearchRideOutputDTO toSearchRideOutputDTO(Ride ride) {
        return SearchRideOutputDTO.builder()
                .id(ride.getId().getValue())
                .passengerId(ride.getPassengerId().getValue())
                .driverId(ride.getDriverId() != null ? ride.getDriverId().getValue() : null)
                .status(ride.getStatus().getValue().name())
                .fare(ride.getFare() != null ? ride.getFare().getValue() : null)
                .distance(ride.getDistance() != null ? ride.getDistance().getValue() : null)
                .latitudeFrom(ride.getFrom().getLatitude())
                .longitudeFrom(ride.getFrom().getLongitude())
                .latitudeTo(ride.getTo().getLatitude())
                .longitudeTo(ride.getTo().getLongitude())
                .build();
    }

}
