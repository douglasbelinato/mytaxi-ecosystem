package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.createaccount;

import br.com.mytaxi.application.usecase.feature.createaccount.CreateAccountUseCase;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.createaccount.CreateAccountRQ;
import br.com.mytaxi.infrastructure.interfaces.input.rest.mapper.account.AccountRestMapper;
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
public class CreateAccountController {

    private final CreateAccountUseCase createAccountUseCase;

    @PostMapping("/accounts")
    public ResponseEntity<Object> execute(@RequestBody CreateAccountRQ request) {
        var outputDto = createAccountUseCase.execute(AccountRestMapper.toCreateAccountDTO(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountRestMapper.toAccountCreatedRS(outputDto));
    }
}

