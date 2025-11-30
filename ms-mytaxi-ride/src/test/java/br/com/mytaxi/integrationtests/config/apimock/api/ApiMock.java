package br.com.mytaxi.integrationtests.config.apimock.api;

import br.com.mytaxi.integrationtests.config.apimock.data.ApiMockTypeEnum;

public interface ApiMock {

    void mock();

    ApiMockTypeEnum getType();

}
