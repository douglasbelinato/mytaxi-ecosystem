package br.com.mytaxi.integrationtests.config.apimock.data.accountsearch;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.integrationtests.config.apimock.data.ApiMockData;
import br.com.mytaxi.integrationtests.config.apimock.data.ApiMockTypeEnum;
import lombok.Builder;

@Builder
public record AccountSearchApiMockData(AccountDTO account)
        implements ApiMockData {

    @Override
    public ApiMockTypeEnum forType() {
        return ApiMockTypeEnum.ACCOUNT_SEARCH;
    }

}
