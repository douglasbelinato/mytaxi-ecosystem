package br.com.mytaxi.infrastructure.input.rest.controller.requestride;

import br.com.mytaxi.application.usecase.feature.requestride.RequestRideUseCase;
import br.com.mytaxi.infrastructure.input.rest.dto.requestride.RequestRideRQ;
import br.com.mytaxi.infrastructure.input.rest.mapper.ride.RideRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class RequestRideController {

    private final RequestRideUseCase requestRideUseCase;
    private final RideRestMapper rideRestMapper;

    @PostMapping("/rides")
    public ResponseEntity<Object> execute(@RequestBody RequestRideRQ request) {
        var outputDTO = requestRideUseCase.execute(rideRestMapper.toRequestRideInputDTO(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(rideRestMapper.toRequestRideRS(outputDTO));
    }

}
