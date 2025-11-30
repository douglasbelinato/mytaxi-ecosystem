package br.com.mytaxi.infrastructure.output.rest.gateway.account;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.application.output.rest.gateway.account.SearchAccountGateway;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.infrastructure.output.rest.client.account.SearchAccountRestClient;
import br.com.mytaxi.infrastructure.output.rest.mapper.account.AccountGatewayMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class SearchAccountGatewayImpl implements SearchAccountGateway {

    private final SearchAccountRestClient restClient;
    private final AccountGatewayMapper accountGatewayMapper;

    @Override
    public AccountDTO search(Id id) {
        return accountGatewayMapper.toAccountDTO(restClient.execute(id.getValue()));
    }

}
