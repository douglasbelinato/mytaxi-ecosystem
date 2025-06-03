package br.com.mytaxi.infrastructure.config.test.apimock.data.accountsearch;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.infrastructure.config.test.apimock.data.ApiMockData;
import br.com.mytaxi.infrastructure.config.test.apimock.data.ApiMockTypeEnum;
import lombok.Builder;

@Builder
public record AccountSearchApiMockData(AccountDTO account)
        implements ApiMockData {

    @Override
    public ApiMockTypeEnum forType() {
        return ApiMockTypeEnum.ACCOUNT_SEARCH;
    }

}
