package br.com.mytaxi.domain.repository.ride;

import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.ride.Ride;
import br.com.mytaxi.domain.model.ride.RideStatusEnum;

import java.util.List;

public interface RideRepository {

    Ride save(Ride ride);

    Ride findByIdOrThrow(Id id);

    boolean existsByPassengerIdAndStatusNot(Id passengerId, RideStatusEnum status);

    boolean existsByDriverIdAndStatusIn(Id driverId, List<RideStatusEnum> statusList);

}
