package br.com.mytaxi.infrastructure.config.test.apimock.api;

import br.com.mytaxi.infrastructure.config.test.apimock.data.ApiMockTypeEnum;

public interface ApiMock {

    void mock();

    ApiMockTypeEnum getType();

}
