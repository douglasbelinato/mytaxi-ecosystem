package br.com.mytaxi.infrastructure.interfaces.output.database.repository.ride;

import br.com.mytaxi.domain.exception.NotFoundException;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.ride.Ride;
import br.com.mytaxi.domain.model.ride.RideStatusEnum;
import br.com.mytaxi.domain.repository.ride.RideRepository;
import br.com.mytaxi.infrastructure.interfaces.output.database.entity.ride.RideEntity;
import br.com.mytaxi.infrastructure.interfaces.output.database.mapper.ride.RideDatabaseMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Named
@RequiredArgsConstructor
class RideRepositoryImpl implements RideRepository {

    private final RideJpaRepository rideJpaRepository;

    @Override
    public Ride save(Ride ride) {
        var entity = rideJpaRepository.save(RideDatabaseMapper.toEntity(ride));
        return mapDomainFromEntity(entity);
    }

    @Override
    public Ride findByIdOrThrow(Id id) {
        var rideEntity = rideJpaRepository.findById(id.getValue())
                .orElseThrow(() -> new NotFoundException("validation.ride.not.found"));
        return mapDomainFromEntity(rideEntity);
    }

    @Override
    public boolean existsByPassengerIdAndStatusNot(Id passengerId, RideStatusEnum status) {
        return rideJpaRepository.findByPassengerIdAndStatusNot(passengerId.getValue(), status.name()).isPresent();
    }

    @Override
    public boolean existsByDriverIdAndStatusIn(Id driverId, List<RideStatusEnum> statusList) {
        return rideJpaRepository.existsByDriverIdAndStatusIn(driverId.getValue(),
                statusList.stream().map(Enum::name).toList());
    }

    private Ride mapDomainFromEntity(RideEntity entity) {
        return RideDatabaseMapper.toDomain(entity).getValue();
    }
}
