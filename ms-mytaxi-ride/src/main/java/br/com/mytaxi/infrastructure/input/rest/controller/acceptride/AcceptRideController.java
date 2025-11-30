package br.com.mytaxi.infrastructure.input.rest.controller.acceptride;

import br.com.mytaxi.application.usecase.feature.acceptride.AcceptRideUseCase;
import br.com.mytaxi.infrastructure.input.rest.dto.acceptride.AcceptRideRQ;
import br.com.mytaxi.infrastructure.input.rest.mapper.ride.RideRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AcceptRideController {

    private final AcceptRideUseCase acceptRideUseCase;
    private final RideRestMapper rideRestMapper;

    @PostMapping("/rides/{id}/accept")
    ResponseEntity<Object> execute(@PathVariable String id, @RequestBody AcceptRideRQ request) {
        acceptRideUseCase.execute(rideRestMapper.toAcceptRideInputDTO(id, request));
        return ResponseEntity.noContent().build();
    }

}
