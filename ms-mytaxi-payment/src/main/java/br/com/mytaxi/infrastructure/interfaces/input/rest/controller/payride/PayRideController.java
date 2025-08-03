package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.payride;

import br.com.mytaxi.application.usecase.feature.payride.PayRideUseCase;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.payride.PayRideRQ;
import br.com.mytaxi.infrastructure.interfaces.input.rest.mapper.transaction.TransactionRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PayRideController {

    private final PayRideUseCase payRideUseCase;

    @PostMapping("/payments")
    public ResponseEntity<Object> execute(@RequestBody PayRideRQ request) {
        payRideUseCase.execute(TransactionRestMapper.toPayRideInputDTO(request));
        return ResponseEntity.noContent().build();
    }

}
