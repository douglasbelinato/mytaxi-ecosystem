package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.registerposition;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.infrastructure.config.test.BaseTest;
import br.com.mytaxi.infrastructure.config.test.apimock.data.accountsearch.AccountSearchApiMockData;
import br.com.mytaxi.infrastructure.interfaces.input.rest.controller.sharedstep.AcceptRideSharedStep;
import br.com.mytaxi.infrastructure.interfaces.input.rest.controller.sharedstep.RequestRideSharedStep;
import br.com.mytaxi.infrastructure.interfaces.input.rest.controller.sharedstep.StartRideSharedStep;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.exception.ExceptionRS;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.registerposition.RegisterPositionRQ;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterPositionControllerTest extends BaseTest {

    private static final String PATH = "/v1/rides/%s/positions";

    @Inject
    private RequestRideSharedStep requestRideSharedStep;

    @Inject
    private AcceptRideSharedStep acceptRideSharedStep;

    @Inject
    private StartRideSharedStep startRideSharedStep;

    @Test
    void testC01ShouldRegisterPosition() {
        var passengerId = UUID.randomUUID().toString();
        var driverId = UUID.randomUUID().toString();
        super.apiMockIntegration.mock(createAccountSearchMockData(passengerId, true));
        super.apiMockIntegration.mock(createAccountSearchMockData(driverId, false));
        var requestRideResponse = requestRideSharedStep.success(passengerId);
        acceptRideSharedStep.success(requestRideResponse.id(), driverId);
        startRideSharedStep.success(requestRideResponse.id());
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterPositionRQ.builder().latitude(-24.955282).longitude(-53.459054).build())
                .when()
                .post(String.format(PATH, requestRideResponse.id()))
                .then()
                .statusCode(204);
    }

    @Test
    void testC02ShouldNotRegisterPositionWithInvalidLatitudeAndLongitude() {
        var httpStatus = 422;
        var rideId = UUID.randomUUID();
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterPositionRQ.builder().latitude(-120.0).longitude(-290.0).build())
                .when()
                .post(String.format(PATH, rideId))
                .then()
                .statusCode(httpStatus)
                .extract()
                .as(ExceptionRS.class);
        assertEquals(httpStatus, response.status());
        assertEquals("POST", response.method());
        assertEquals(String.format(PATH, rideId), response.path());
        assertEquals("validation.domain.exception.message", response.message());
        assertEquals(List.of("position.coordinate=>latitude must be between -90 and 90 degrees and longitude must be between -180 and 180 degrees"), response.constraints());
    }

    @Test
    void testC03ShouldNotRegisterPositionWithStatusInvalid() {
        var httpStatus = 422;
        var passengerId = UUID.randomUUID().toString();
        super.apiMockIntegration.mock(createAccountSearchMockData(passengerId, true));
        var requestRideResponse = requestRideSharedStep.success(passengerId);
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterPositionRQ.builder().latitude(-24.955282).longitude(-53.459054).build())
                .when()
                .post(String.format(PATH, requestRideResponse.id()))
                .then()
                .statusCode(httpStatus)
                .extract()
                .as(ExceptionRS.class);
        assertEquals(httpStatus, response.status());
        assertEquals("POST", response.method());
        assertEquals(String.format(PATH, requestRideResponse.id()), response.path());
        assertEquals("validation.domain.exception.message", response.message());
        assertEquals(List.of("validation.ride.must.be.in.progress.to.register.position"), response.constraints());
    }

    @Test
    void testC04ShouldNotRegisterPositionWhenRideDoesNotExist() {
        var httpStatus = 404;
        var rideId = UUID.randomUUID().toString();
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterPositionRQ.builder().latitude(-24.955282).longitude(-53.459054).build())
                .when()
                .post(String.format(PATH, rideId))
                .then()
                .statusCode(httpStatus)
                .extract()
                .as(ExceptionRS.class);
        assertEquals(httpStatus, response.status());
        assertEquals("POST", response.method());
        assertEquals(String.format(PATH, rideId), response.path());
        assertEquals("validation.ride.not.found", response.message());
        assertEquals(Collections.emptyList(), response.constraints());
    }

    private AccountSearchApiMockData createAccountSearchMockData(String id, boolean isPassenger) {
        return AccountSearchApiMockData.builder()
                .account(AccountDTO.builder().id(id).isPassenger(isPassenger).isDriver(!isPassenger).build())
                .build();
    }


}