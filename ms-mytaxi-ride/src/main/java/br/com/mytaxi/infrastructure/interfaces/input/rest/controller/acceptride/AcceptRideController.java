package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.acceptride;

import br.com.mytaxi.application.usecase.feature.acceptride.AcceptRideUseCase;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.acceptride.AcceptRideRQ;
import br.com.mytaxi.infrastructure.interfaces.input.rest.mapper.ride.RideRestMapper;
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

    @PostMapping("/rides/{id}/accept")
    ResponseEntity<Object> execute(@PathVariable String id, @RequestBody AcceptRideRQ request) {
        acceptRideUseCase.execute(RideRestMapper.toAcceptRideInputDTO(id, request));
        return ResponseEntity.noContent().build();
    }

}
