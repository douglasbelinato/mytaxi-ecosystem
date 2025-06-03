package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.registerposition;

import br.com.mytaxi.application.usecase.feature.registerposition.RegisterPositionUseCase;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.registerposition.RegisterPositionRQ;
import br.com.mytaxi.infrastructure.interfaces.input.rest.mapper.position.PositionRestMapper;
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
public class RegisterPositionController {

    private final RegisterPositionUseCase registerPositionUseCase;

    @PostMapping("/rides/{id}/positions")
    public ResponseEntity<Object> execute(@PathVariable String id, @RequestBody RegisterPositionRQ request) {
        registerPositionUseCase.execute(PositionRestMapper.toRegisterPositionInputDTO(id, request));
        return ResponseEntity.noContent().build();
    }

}
