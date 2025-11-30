package br.com.mytaxi.integrationtests.config.apimock.api;

import br.com.mytaxi.integrationtests.config.apimock.data.ApiMockData;
import br.com.mytaxi.integrationtests.config.apimock.data.ApiMockTypeEnum;

public interface ApiMockWithData<T extends ApiMockData> {

    void mock(T data);

    ApiMockTypeEnum getType();

}
