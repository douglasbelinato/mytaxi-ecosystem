package br.com.mytaxi.application.usecase.feature.registerposition;

import br.com.mytaxi.application.mapper.position.PositionApplicationMapper;
import br.com.mytaxi.application.usecase.dto.registerposition.RegisterPositionInputDTO;
import br.com.mytaxi.domain.exception.DomainException;
import br.com.mytaxi.domain.repository.position.PositionRepository;
import br.com.mytaxi.domain.repository.ride.RideRepository;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Named
@RequiredArgsConstructor
class RegisterPositionUseCaseImpl implements RegisterPositionUseCase {

    private final RideRepository rideRepository;
    private final PositionRepository positionRepository;
    private final PositionApplicationMapper positionApplicationMapper;

    @Transactional
    @Override
    public void execute(RegisterPositionInputDTO inputDTO) {
        var position = positionApplicationMapper.toDomain(inputDTO).getValue();
        var ride = rideRepository.findByIdOrThrow(position.getRideId());
        if (ride.getStatus().isNotInProgress()) {
            throw new DomainException("validation.ride.must.be.in.progress.to.register.position");
        }
        ride.updatePosition(inputDTO.latitude(), inputDTO.longitude());
        rideRepository.save(ride);
        positionRepository.save(position);
    }
}
