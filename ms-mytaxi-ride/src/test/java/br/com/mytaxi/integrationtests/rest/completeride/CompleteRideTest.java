package br.com.mytaxi.integrationtests.rest.completeride;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.infrastructure.input.rest.dto.completeride.CompleteRideRQ;
import br.com.mytaxi.infrastructure.input.rest.dto.exception.ExceptionRS;
import br.com.mytaxi.integrationtests.config.BaseTest;
import br.com.mytaxi.integrationtests.config.apimock.data.ApiMockTypeEnum;
import br.com.mytaxi.integrationtests.config.apimock.data.accountsearch.AccountSearchApiMockData;
import br.com.mytaxi.integrationtests.rest.sharedstep.AcceptRideSharedStep;
import br.com.mytaxi.integrationtests.rest.sharedstep.RegisterPositionSharedStep;
import br.com.mytaxi.integrationtests.rest.sharedstep.RequestRideSharedStep;
import br.com.mytaxi.integrationtests.rest.sharedstep.StartRideSharedStep;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompleteRideTest extends BaseTest {

    private static final String PATH = "/v1/rides/%s/complete";

    @Inject
    private RequestRideSharedStep requestRideSharedStep;

    @Inject
    private AcceptRideSharedStep acceptRideSharedStep;

    @Inject
    private StartRideSharedStep startRideSharedStep;

    @Inject
    private RegisterPositionSharedStep registerPositionSharedStep;

    @Test
    void testC01ShouldCompleteARide() {
        var passengerId = UUID.randomUUID().toString();
        var driverId = UUID.randomUUID().toString();
        super.apiMockIntegration.mock(createAccountSearchMockData(passengerId, true));
        super.apiMockIntegration.mock(createAccountSearchMockData(driverId, false));
        super.apiMockIntegration.mock(ApiMockTypeEnum.PAYMENT);
        var requestRideResponse = requestRideSharedStep.success(passengerId);
        acceptRideSharedStep.success(requestRideResponse.id(), driverId);
        startRideSharedStep.success(requestRideResponse.id());
        registerPositionSharedStep.success(requestRideResponse.id(), -23.529287790573242, -46.675448474977);
        registerPositionSharedStep.success(requestRideResponse.id(), -24.908133115138398, -53.49481208223108);
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(CompleteRideRQ.builder().creditCardToken("card_1N3T00LkdIwHu7ixRdxpVI1Q").build())
                .when()
                .post(String.format(PATH, requestRideResponse.id()))
                .then()
                .statusCode(204);
    }

    @Test
    void testC02ShouldNotCompleteARideWithStatusInvalid() {
        var httpStatus = 422;
        var passengerId = UUID.randomUUID().toString();
        super.apiMockIntegration.mock(createAccountSearchMockData(passengerId, true));
        var requestRideResponse = requestRideSharedStep.success(passengerId);
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(CompleteRideRQ.builder().creditCardToken("card_1N3T00LkdIwHu7ixRdxpVI1Q").build())
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
        assertEquals(List.of("validation.invalid.ride.status.to.be.completed"), response.constraints());
    }

    @Test
    void testC03ShouldNotCompleteARideThatDoesNotExist() {
        var httpStatus = 404;
        var rideId = UUID.randomUUID().toString();
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(CompleteRideRQ.builder().creditCardToken("card_1N3T00LkdIwHu7ixRdxpVI1Q").build())
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