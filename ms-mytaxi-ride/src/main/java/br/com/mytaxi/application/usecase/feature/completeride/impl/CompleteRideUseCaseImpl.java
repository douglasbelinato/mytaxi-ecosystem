package br.com.mytaxi.application.usecase.feature.completeride.impl;

import br.com.mytaxi.application.output.rest.gateway.payment.PaymentGateway;
import br.com.mytaxi.application.usecase.dto.completeride.CompleteRideInputDTO;
import br.com.mytaxi.application.usecase.feature.completeride.CompleteRideUseCase;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.repository.position.PositionRepository;
import br.com.mytaxi.domain.repository.ride.RideRepository;
import br.com.mytaxi.domain.service.distance.TotalDistanceCalculatorService;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class CompleteRideUseCaseImpl implements CompleteRideUseCase {

    private final RideRepository rideRepository;
    private final PositionRepository positionRepository;
    private final TotalDistanceCalculatorService totalDistanceCalculatorService;
    private final PaymentGateway paymentGateway;

    @Override
    public void execute(CompleteRideInputDTO inputDTO) {
        var id = Id.of("id", inputDTO.id()).getValue();
        var ride = rideRepository.findByIdOrThrow(id);
        var positions = positionRepository.findAllByRideId(ride.getId());
        var totalDistance = totalDistanceCalculatorService.execute(positions);
        ride.complete(totalDistance);
        rideRepository.save(ride);
        paymentGateway.process(ride.getId(), ride.getFare(), inputDTO.creditCardToken());
    }
}
