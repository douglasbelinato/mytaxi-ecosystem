package br.com.mytaxi.application.usecase.feature.completeride;

import br.com.mytaxi.application.mapper.json.JsonMapper;
import br.com.mytaxi.application.usecase.dto.completeride.CompleteRideInputDTO;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.domain.model.event.Event;
import br.com.mytaxi.domain.model.event.EventType;
import br.com.mytaxi.domain.model.event.RideCompletedEventPayload;
import br.com.mytaxi.domain.repository.event.EventRepository;
import br.com.mytaxi.domain.repository.position.PositionRepository;
import br.com.mytaxi.domain.repository.ride.RideRepository;
import br.com.mytaxi.domain.service.distance.TotalDistanceCalculatorService;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Named
@RequiredArgsConstructor
class CompleteRideUseCaseImpl implements CompleteRideUseCase {

    private static final int ONE = 1;

    private final RideRepository rideRepository;
    private final EventRepository eventRepository;
    private final PositionRepository positionRepository;
    private final TotalDistanceCalculatorService totalDistanceCalculatorService;
    private final JsonMapper jsonMapper;

    @Transactional
    @Override
    public void execute(CompleteRideInputDTO inputDTO) {
        var id = Id.of("id", inputDTO.id()).getValue();
        var ride = rideRepository.findByIdOrThrow(id);
        var positions = positionRepository.findAllByRideId(ride.getId());
        var totalDistance = totalDistanceCalculatorService.execute(positions);
        ride.complete(totalDistance);
        rideRepository.save(ride);
        eventRepository.save(Event.create(ride.getId().getValue(), EventType.RIDE_COMPLETED,
                jsonMapper.toJson(RideCompletedEventPayload.builder()
                        .version(ONE)
                        .rideId(ride.getId().getValue())
                        .amount(ride.getFare().getValue())
                        .creditCardToken(inputDTO.creditCardToken())
                        .build())));
    }
}
