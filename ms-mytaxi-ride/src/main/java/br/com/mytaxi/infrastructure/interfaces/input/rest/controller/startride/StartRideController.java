package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.startride;

import br.com.mytaxi.application.usecase.feature.startride.StartRideUseCase;
import br.com.mytaxi.infrastructure.interfaces.input.rest.mapper.ride.RideRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class StartRideController {

    private final StartRideUseCase startRideUseCase;

    @PostMapping("/rides/{id}/start")
    ResponseEntity<Object> execute(@PathVariable String id) {
        startRideUseCase.execute(RideRestMapper.toStartRideInputDTO(id));
        return ResponseEntity.noContent().build();
    }

}
