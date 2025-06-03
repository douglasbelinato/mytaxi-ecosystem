package br.com.mytaxi.application.usecase.feature.requestride.impl;

import br.com.mytaxi.application.mapper.ride.RideApplicationMapper;
import br.com.mytaxi.application.output.rest.gateway.account.SearchAccountGateway;
import br.com.mytaxi.application.usecase.dto.requestride.RequestRideInputDTO;
import br.com.mytaxi.application.usecase.dto.requestride.RequestRideOutputDTO;
import br.com.mytaxi.application.usecase.feature.requestride.RequestRideUseCase;
import br.com.mytaxi.domain.exception.DomainException;
import br.com.mytaxi.domain.model.ride.RideStatusEnum;
import br.com.mytaxi.domain.repository.ride.RideRepository;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class RequestRideUseCaseImpl implements RequestRideUseCase {

    private final SearchAccountGateway searchAccountGateway;
    private final RideRepository rideRepository;

    @Override
    public RequestRideOutputDTO execute(RequestRideInputDTO inputDTO) {
        var candidate = RideApplicationMapper.toDomain(inputDTO);
        candidate.validate();
        var ride = candidate.getValue();
        var accountDTO = searchAccountGateway.search(ride.getPassengerId());
        if (accountDTO.isDriver()) {
            throw new DomainException("validation.ride.account.do.not.belong.to.a.passenger");
        }
        if (rideRepository.existsByPassengerIdAndStatusNot(ride.getPassengerId(), RideStatusEnum.COMPLETED)) {
            throw new DomainException("validation.active.ride.found");
        }
        ride = rideRepository.save(ride);
        return RideApplicationMapper.toRequestRideOutputDTO(ride);
    }

}
