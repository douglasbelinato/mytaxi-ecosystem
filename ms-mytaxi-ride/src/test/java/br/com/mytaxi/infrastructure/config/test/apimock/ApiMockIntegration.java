package br.com.mytaxi.infrastructure.config.test.apimock;

import br.com.mytaxi.infrastructure.config.test.apimock.api.ApiMock;
import br.com.mytaxi.infrastructure.config.test.apimock.data.ApiMockData;
import com.github.tomakehurst.wiremock.WireMockServer;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Named
@RequiredArgsConstructor
public class ApiMockIntegration {

    private final WireMockServer wireMockServer;
    private final List<ApiMock> apiMockList;

    @SuppressWarnings("unchecked")
    public void mock(ApiMockData data) {
        if (data == null) throw new IllegalArgumentException("");
        var apiMock = apiMockList.stream().filter(i -> i.getType().equals(data.forType())).findFirst().orElseThrow();
        apiMock.mock(data);
    }

    public void resetAll() {
        wireMockServer.resetAll();
    }

}
