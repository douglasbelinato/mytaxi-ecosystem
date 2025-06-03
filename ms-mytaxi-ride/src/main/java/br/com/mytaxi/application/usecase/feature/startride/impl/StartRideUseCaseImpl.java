package br.com.mytaxi.application.usecase.feature.startride.impl;

import br.com.mytaxi.application.usecase.dto.startride.StartRideInputDTO;
import br.com.mytaxi.application.usecase.feature.startride.StartRideUseCase;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.repository.ride.RideRepository;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class StartRideUseCaseImpl implements StartRideUseCase {

    private final RideRepository rideRepository;

    @Override
    public void execute(StartRideInputDTO inputDTO) {
        var id = Id.of("id", inputDTO.id()).getValue();
        var ride = rideRepository.findByIdOrThrow(id);
        ride.start();
        rideRepository.save(ride);
    }
}
