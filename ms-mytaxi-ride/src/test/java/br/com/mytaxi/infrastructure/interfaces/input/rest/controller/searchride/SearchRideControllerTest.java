package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.searchride;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.domain.model.ride.RideStatusEnum;
import br.com.mytaxi.infrastructure.config.test.BaseTest;
import br.com.mytaxi.infrastructure.config.test.apimock.data.accountsearch.AccountSearchApiMockData;
import br.com.mytaxi.infrastructure.interfaces.input.rest.controller.sharedstep.RequestRideSharedStep;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.exception.ExceptionRS;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.requestride.RequestRideRQ;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.searchride.RideRS;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SearchRideControllerTest extends BaseTest {

    public static final String PATH = "/v1/rides/";

    @Inject
    private RequestRideSharedStep requestRideSharedStep;

    @Test
    void testC01ShouldSuccessfullySearchARequestedRide() {
        var passengerId = UUID.randomUUID().toString();
        super.apiMockIntegration.mock(AccountSearchApiMockData.builder()
                .account(AccountDTO.builder().id(passengerId).isPassenger(true).build())
                .build());
        var request = RequestRideRQ.builder()
                .passengerId(passengerId)
                .latitudeFrom(-23.529287790573242)
                .longitudeFrom(-46.675448474977)
                .latitudeTo(-24.908133115138398)
                .longitudeTo(-53.49481208223108)
                .build();
        var createResponse = requestRideSharedStep.success(request);
        var searchResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(PATH + createResponse.id())
                .then()
                .statusCode(200)
                .extract()
                .as(RideRS.class);
        assertDoesNotThrow(() -> UUID.fromString(searchResponse.id()));
        assertEquals(request.passengerId(), searchResponse.passengerId());
        assertEquals("Alice", searchResponse.passengerName());
        assertEquals("Silva", searchResponse.passengerSurname());
        assertEquals("alice@host.com.br", searchResponse.passengerEmail());
        assertNull(searchResponse.driverId());
        assertEquals(request.latitudeFrom(), searchResponse.latitudeFrom());
        assertEquals(request.longitudeFrom(), searchResponse.longitudeFrom());
        assertEquals(request.latitudeTo(), searchResponse.latitudeTo());
        assertEquals(request.longitudeTo(), searchResponse.longitudeTo());
        assertEquals(RideStatusEnum.REQUESTED.name(), searchResponse.status());
    }

    @Test
    void testC02ShouldNotFoundARide() {
        var path = PATH + UUID.randomUUID();
        var httpStatus = 404;
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(path)
                .then()
                .statusCode(httpStatus)
                .extract()
                .as(ExceptionRS.class);
        assertEquals(httpStatus, response.status());
        assertEquals("GET", response.method());
        assertEquals(path, response.path());
        assertEquals("validation.ride.not.found", response.message());
        assertEquals(Collections.emptyList(), response.constraints());
    }

}