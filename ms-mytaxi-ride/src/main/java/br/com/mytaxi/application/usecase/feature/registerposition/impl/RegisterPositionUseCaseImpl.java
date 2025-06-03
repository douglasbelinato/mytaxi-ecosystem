package br.com.mytaxi.application.usecase.feature.registerposition.impl;

import br.com.mytaxi.application.usecase.dto.registerposition.RegisterPositionInputDTO;
import br.com.mytaxi.application.usecase.feature.registerposition.RegisterPositionUseCase;
import br.com.mytaxi.domain.exception.DomainException;
import br.com.mytaxi.domain.model.position.Position;
import br.com.mytaxi.domain.repository.position.PositionRepository;
import br.com.mytaxi.domain.repository.ride.RideRepository;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class RegisterPositionUseCaseImpl implements RegisterPositionUseCase {

    private final RideRepository rideRepository;
    private final PositionRepository positionRepository;

    @Override
    public void execute(RegisterPositionInputDTO inputDTO) {
        var positionCandidate = Position.create(inputDTO.rideId(), inputDTO.latitude(), inputDTO.longitude());
        positionCandidate.validate();
        var position = positionCandidate.getValue();
        var ride = rideRepository.findByIdOrThrow(position.getRideId());
        if (ride.getStatus().isNotInProgress()) {
            throw new DomainException("validation.ride.must.be.in.progress.to.register.position");
        }
        positionRepository.save(position);
    }
}
