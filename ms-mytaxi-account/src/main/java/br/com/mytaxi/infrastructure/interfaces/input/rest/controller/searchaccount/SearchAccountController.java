package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.searchaccount;

import br.com.mytaxi.application.usecase.feature.searchaccount.SearchAccountUseCase;
import br.com.mytaxi.infrastructure.interfaces.input.rest.mapper.account.AccountRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SearchAccountController {

    private final SearchAccountUseCase searchAccountUseCase;

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> execute(@PathVariable String id) {
        var outputDto = searchAccountUseCase.execute(AccountRestMapper.toSearchAccountDTO(id));
        return ResponseEntity.ok().body(AccountRestMapper.toAccountRS(outputDto));
    }

}
