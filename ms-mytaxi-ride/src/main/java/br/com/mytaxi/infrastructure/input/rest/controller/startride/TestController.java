package br.com.mytaxi.infrastructure.input.rest.controller.startride;

import br.com.mytaxi.application.usecase.feature.produceevent.PublishEventUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TestController {

    private final PublishEventUseCase publishEventUseCase;

    @PostMapping("/events")
    ResponseEntity<Object> execute() {
        publishEventUseCase.execute();
        return ResponseEntity.noContent().build();
    }

}
