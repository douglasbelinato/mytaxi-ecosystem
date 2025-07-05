package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.completeride;

import br.com.mytaxi.application.usecase.feature.completeride.CompleteRideUseCase;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.completeride.CompleteRideRQ;
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
public class CompleteRideController {

    private final CompleteRideUseCase completeRideUseCase;

    @PostMapping("/rides/{id}/complete")
    public ResponseEntity<Object> execute(@PathVariable String id, @RequestBody CompleteRideRQ request) {
        completeRideUseCase.execute(RideRestMapper.toCompleteRideInputDTO(id, request));
        return ResponseEntity.noContent().build();
    }

}
