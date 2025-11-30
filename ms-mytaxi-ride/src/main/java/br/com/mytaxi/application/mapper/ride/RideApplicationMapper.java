package br.com.mytaxi.application.mapper.ride;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.application.usecase.dto.requestride.RequestRideInputDTO;
import br.com.mytaxi.application.usecase.dto.requestride.RequestRideOutputDTO;
import br.com.mytaxi.application.usecase.dto.searchride.SearchRideOutputDTO;
import br.com.mytaxi.domain.model.common.Candidate;
import br.com.mytaxi.domain.model.ride.Ride;
import br.com.mytaxi.domain.model.ride.RideStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RideApplicationMapper {

    default Candidate<Ride> toDomain(RequestRideInputDTO dto) {
        return Ride.create(dto.passengerId(), dto.latitudeFrom(), dto.longitudeFrom(),
                dto.latitudeTo(), dto.longitudeTo());
    }

    @Mapping(target = "id", source = "id.value")
    RequestRideOutputDTO toRequestRideOutputDTO(Ride ride);

    @Mapping(target = "id", source = "ride.id.value")
    @Mapping(target = "passengerId", source = "passengerAccount.id")
    @Mapping(target = "passengerName", source = "passengerAccount.name")
    @Mapping(target = "passengerSurname", source = "passengerAccount.surname")
    @Mapping(target = "passengerEmail", source = "passengerAccount.email")
    @Mapping(target = "driverId", source = "driverAccount.id")
    @Mapping(target = "driverName", source = "driverAccount.name")
    @Mapping(target = "driverSurname", source = "driverAccount.surname")
    @Mapping(target = "driverEmail", source = "driverAccount.email")
    @Mapping(target = "status", source = "ride.status.value")
    @Mapping(target = "fare", source = "ride.fare.value")
    @Mapping(target = "distance", source = "ride.distance.value")
    @Mapping(target = "latitudeFrom", source = "ride.from.latitude")
    @Mapping(target = "longitudeFrom", source = "ride.from.longitude")
    @Mapping(target = "latitudeTo", source = "ride.to.latitude")
    @Mapping(target = "longitudeTo", source = "ride.to.longitude")
    SearchRideOutputDTO toSearchRideOutputDTO(Ride ride, AccountDTO passengerAccount, AccountDTO driverAccount);


}
