package br.com.mytaxi.application.output.rest.gateway.account;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.domain.model.common.Id;

public interface SearchAccountGateway {

    AccountDTO search(Id id);

}
