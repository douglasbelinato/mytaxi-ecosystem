package br.com.mytaxi.integrationtests.rest.requestride;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.integrationtests.config.BaseTest;
import br.com.mytaxi.integrationtests.config.apimock.data.accountsearch.AccountSearchApiMockData;
import br.com.mytaxi.integrationtests.rest.sharedstep.RequestRideSharedStep;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestRideTest extends BaseTest {

    private static final String PATH = "/v1/rides";

    @Inject
    private RequestRideSharedStep requestRideSharedStep;

    @Test
    void testC01ShouldRequestARide() {
        var passengerId = UUID.randomUUID().toString();
        super.apiMockIntegration.mock(createAccountSearchMockData(passengerId, true));
        var response = requestRideSharedStep.success(passengerId);
        assertDoesNotThrow(() -> UUID.fromString(response.id()));
    }

    @Test
    void testC02ShouldNotRequestARideForADriver() {
        var driverId = UUID.randomUUID().toString();
        super.apiMockIntegration.mock(createAccountSearchMockData(driverId, false));
        var httpStatus = 422;
        var response = requestRideSharedStep.unsuccess(driverId, httpStatus);
        assertEquals(httpStatus, response.status());
        assertEquals("POST", response.method());
        assertEquals(PATH, response.path());
        assertEquals("validation.domain.exception.message", response.message());
        assertEquals(List.of("validation.ride.account.do.not.belong.to.a.passenger"), response.constraints());
    }

    @Test
    void testC03ShouldNotRequestARideForAPassengerWithOpenedRide() {
        var passengerId = UUID.randomUUID().toString();
        super.apiMockIntegration.mock(createAccountSearchMockData(passengerId, true));
        var httpStatus = 422;
        requestRideSharedStep.success(passengerId);
        var response = requestRideSharedStep.unsuccess(passengerId, httpStatus);
        assertEquals(httpStatus, response.status());
        assertEquals("POST", response.method());
        assertEquals(PATH, response.path());
        assertEquals("validation.domain.exception.message", response.message());
        assertEquals(List.of("validation.active.ride.found"), response.constraints());
    }

    private AccountSearchApiMockData createAccountSearchMockData(String id, boolean isPassenger) {
        return AccountSearchApiMockData.builder()
                .account(AccountDTO.builder().id(id).isPassenger(isPassenger).isDriver(!isPassenger).build())
                .build();
    }

}