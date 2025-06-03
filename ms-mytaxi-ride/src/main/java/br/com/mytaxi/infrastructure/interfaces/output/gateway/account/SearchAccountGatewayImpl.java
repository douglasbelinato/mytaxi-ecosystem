package br.com.mytaxi.infrastructure.interfaces.output.gateway.account;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.application.output.rest.gateway.account.SearchAccountGateway;
import br.com.mytaxi.domain.model.common.Id;
import br.com.mytaxi.infrastructure.interfaces.output.gateway.mapper.account.AccountGatewayMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class SearchAccountGatewayImpl implements SearchAccountGateway {

    private final SearchAccountClient client;

    @Override
    public AccountDTO search(Id id) {
        return AccountGatewayMapper.toAccountDTO(client.execute(id.getValue()));
    }

}
