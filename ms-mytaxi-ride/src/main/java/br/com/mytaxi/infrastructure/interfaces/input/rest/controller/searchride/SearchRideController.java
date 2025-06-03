package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.searchride;

import br.com.mytaxi.application.usecase.feature.searchride.SearchRideUseCase;
import br.com.mytaxi.infrastructure.interfaces.input.rest.mapper.ride.RideRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SearchRideController {

    private final SearchRideUseCase searchRideUseCase;

    @GetMapping("/rides/{id}")
    public ResponseEntity<Object> execute(@PathVariable String id) {
        var outputDTO = searchRideUseCase.execute(RideRestMapper.toSearchRideInputDTO(id));
        return ResponseEntity.ok().body(RideRestMapper.toSearchRideRS(outputDTO));
    }

}
