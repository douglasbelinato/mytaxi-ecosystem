package br.com.mytaxi.infrastructure.config.test.apimock;

import br.com.mytaxi.infrastructure.config.test.apimock.api.ApiMock;
import br.com.mytaxi.infrastructure.config.test.apimock.api.ApiMockWithData;
import br.com.mytaxi.infrastructure.config.test.apimock.data.ApiMockData;
import br.com.mytaxi.infrastructure.config.test.apimock.data.ApiMockTypeEnum;
import com.github.tomakehurst.wiremock.WireMockServer;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Named
@RequiredArgsConstructor
public final class ApiMockIntegration {

    private final WireMockServer wireMockServer;
    private final List<ApiMockWithData> apiMockWithDataList;
    private final List<ApiMock> apiMockList;

    @SuppressWarnings("unchecked")
    public void mock(ApiMockData data) {
        if (data == null) throw new IllegalArgumentException("");
        var apiMock = apiMockWithDataList.stream().filter(i -> i.getType().equals(data.forType())).findFirst().orElseThrow();
        apiMock.mock(data);
    }

    public void mock(ApiMockTypeEnum type) {
        var apiMock = apiMockList.stream().filter(i -> i.getType().equals(type)).findFirst().orElseThrow();
        apiMock.mock();
    }

    public void resetAll() {
        wireMockServer.resetAll();
    }

}
