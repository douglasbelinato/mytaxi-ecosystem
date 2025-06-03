package br.com.mytaxi.infrastructure.interfaces.output.gateway.account;

import br.com.mytaxi.infrastructure.interfaces.output.gateway.dto.account.SearchAccountRS;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "searchAccountClient", url = "${integration.account.host}")
public interface SearchAccountClient {

    @GetMapping(value = "/v1/accounts/{id}")
    SearchAccountRS execute(@PathVariable String id);

}
