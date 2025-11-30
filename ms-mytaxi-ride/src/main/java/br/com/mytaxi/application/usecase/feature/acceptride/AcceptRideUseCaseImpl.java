package br.com.mytaxi.application.usecase.feature.acceptride;

import br.com.mytaxi.application.output.rest.gateway.account.SearchAccountGateway;
import br.com.mytaxi.application.usecase.dto.acceptride.AcceptRideInputDTO;
import br.com.mytaxi.domain.exception.DomainException;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.ride.RideStatusEnum;
import br.com.mytaxi.domain.repository.ride.RideRepository;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Named
@RequiredArgsConstructor
class AcceptRideUseCaseImpl implements AcceptRideUseCase {

    private final SearchAccountGateway searchAccountGateway;
    private final RideRepository rideRepository;

    @Override
    public void execute(AcceptRideInputDTO inputDTO) {
        var rideId = Id.of("id", inputDTO.id()).getValue();
        var driverId = Id.of("driverId", inputDTO.driverId()).getValue();
        var ride = rideRepository.findByIdOrThrow(rideId);
        var accountDTO = searchAccountGateway.search(driverId);
        if (accountDTO.isPassenger()) {
            throw new DomainException("validation.ride.account.do.not.belong.to.a.driver");
        }
        if (rideRepository.existsByDriverIdAndStatusIn(driverId,
                List.of(RideStatusEnum.ACCEPTED, RideStatusEnum.IN_PROGRESS))) {
            throw new DomainException("validation.driver.already.has.a.ride.assigned");
        }
        ride.accept(driverId);
        rideRepository.save(ride);
    }

}
