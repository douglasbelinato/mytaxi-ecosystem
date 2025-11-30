package br.com.mytaxi.infrastructure.output.rest.mapper.account;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.infrastructure.output.rest.dto.account.SearchAccountRS;
import org.mapstruct.Mapper;

@Mapper
public interface AccountGatewayMapper {

    AccountDTO toAccountDTO(SearchAccountRS response);

}