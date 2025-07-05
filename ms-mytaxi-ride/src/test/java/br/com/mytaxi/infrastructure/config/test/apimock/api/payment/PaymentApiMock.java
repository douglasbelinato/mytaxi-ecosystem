package br.com.mytaxi.infrastructure.config.test.apimock.api.payment;

import br.com.mytaxi.infrastructure.config.test.apimock.api.ApiMock;
import br.com.mytaxi.infrastructure.config.test.apimock.data.ApiMockTypeEnum;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@Named
@RequiredArgsConstructor
class PaymentApiMock implements ApiMock {

    private final WireMockServer wireMockServer;

    @Override
    public void mock() {
        wireMockServer.stubFor(post(urlEqualTo("/v1/payments"))
                .willReturn(WireMock.aResponse().withStatus(204)));
    }

    @Override
    public ApiMockTypeEnum getType() {
        return ApiMockTypeEnum.PAYMENT;
    }

}
