package br.com.mytaxi.integrationtests.config.apimock.api.accountsearch;

import br.com.mytaxi.integrationtests.config.apimock.api.ApiMockWithData;
import br.com.mytaxi.integrationtests.config.apimock.data.ApiMockTypeEnum;
import br.com.mytaxi.integrationtests.config.apimock.data.accountsearch.AccountSearchApiMockData;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@Named
@RequiredArgsConstructor
class AccountSearchApiMockWithData implements ApiMockWithData<AccountSearchApiMockData> {

    private final WireMockServer wireMockServer;

    @Override
    public void mock(AccountSearchApiMockData data) {
        wireMockServer.stubFor(get(urlEqualTo("/v1/accounts/" + data.account().id()))
                .willReturn(WireMock.aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("account/AccountSearchSuccess.json")
                        .withTransformerParameter("id", data.account().id())
                        .withTransformerParameter("isPassenger", data.account().isPassenger())
                        .withTransformerParameter("isDriver", data.account().isDriver())
                ));
    }

    @Override
    public ApiMockTypeEnum getType() {
        return ApiMockTypeEnum.ACCOUNT_SEARCH;
    }

}
