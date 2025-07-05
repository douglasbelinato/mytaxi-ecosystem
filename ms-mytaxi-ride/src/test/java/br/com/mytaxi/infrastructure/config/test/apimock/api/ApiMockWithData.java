package br.com.mytaxi.infrastructure.config.test.apimock.api;

import br.com.mytaxi.infrastructure.config.test.apimock.data.ApiMockData;
import br.com.mytaxi.infrastructure.config.test.apimock.data.ApiMockTypeEnum;

public interface ApiMockWithData<T extends ApiMockData> {

    void mock(T data);

    ApiMockTypeEnum getType();

}
