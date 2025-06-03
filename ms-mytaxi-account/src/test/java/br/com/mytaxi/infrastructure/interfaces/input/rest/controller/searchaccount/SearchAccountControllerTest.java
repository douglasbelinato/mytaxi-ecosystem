package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.searchaccount;

import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.createaccount.AccountCreatedRS;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.createaccount.CreateAccountRQ;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.exception.ExceptionRS;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.searchaccount.AccountRS;
import br.com.mytaxi.infrastructure.config.BaseSpringTest;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SearchAccountControllerTest extends BaseSpringTest {

    private static final String CREATE_PATH = "/v1/accounts";
    private static final String SEARCH_PATH = CREATE_PATH + "/%s";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testSearchAccount() {
        var request = CreateAccountRQ.builder()
                .name("Julia")
                .surname("Silva")
                .email("julia@host.com")
                .cpf("525.118.173-65")
                .password("pass123")
                .isPassenger(true)
                .build();
        var createResponseEntity = restTemplate.postForEntity(CREATE_PATH, request, AccountCreatedRS.class);
        var createResponse = createResponseEntity.getBody();
        assertNotNull(createResponse);
        var searchResponseEntity = restTemplate.getForEntity(getSearchPath(createResponse.id()), AccountRS.class);
        var searchResponse = searchResponseEntity.getBody();
        assertEquals(HttpStatus.OK, searchResponseEntity.getStatusCode());
        assertNotNull(searchResponse);
        assertDoesNotThrow(() -> UUID.fromString(searchResponse.id()));
        assertEquals(request.name(), searchResponse.name());
        assertEquals(request.surname(), searchResponse.surname());
        assertEquals(request.email(), searchResponse.email());
        assertEquals(request.cpf(), searchResponse.cpf());
        assertEquals(request.carPlate(), searchResponse.carPlate());
        assertEquals(request.isPassenger(), searchResponse.isPassenger());
        assertEquals(request.isDriver(), searchResponse.isDriver());
    }

    private String getSearchPath(String id) {
        return String.format(SEARCH_PATH, ObjectUtils.defaultIfNull(id, ""));
    }

    @Test
    void testSearchAccountThatDoesNotExist() {
        var httpStatus = HttpStatus.NOT_FOUND;
        var searchPath = getSearchPath(UUID.randomUUID().toString());
        var searchResponseEntity = restTemplate.getForEntity(searchPath, ExceptionRS.class);
        assertEquals(httpStatus, searchResponseEntity.getStatusCode());
        var searchResponse = searchResponseEntity.getBody();
        assertNotNull(searchResponse);
        assertEquals(httpStatus.value(), searchResponse.status());
        assertEquals("GET", searchResponse.method());
        assertEquals(searchPath, searchResponse.path());
        assertEquals("Account not found", searchResponse.message());
        assertEquals(Collections.emptyList(), searchResponse.constraints());
    }

}