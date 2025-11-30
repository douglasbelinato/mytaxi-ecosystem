package br.com.mytaxi.integrationtests.rest.startride;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.integrationtests.config.BaseTest;
import br.com.mytaxi.integrationtests.config.apimock.data.accountsearch.AccountSearchApiMockData;
import br.com.mytaxi.integrationtests.rest.sharedstep.AcceptRideSharedStep;
import br.com.mytaxi.integrationtests.rest.sharedstep.RequestRideSharedStep;
import br.com.mytaxi.integrationtests.rest.sharedstep.StartRideSharedStep;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StartRideTest extends BaseTest {

    private static final String PATH = "/v1/rides/%s/start";

    @Inject
    private RequestRideSharedStep requestRideSharedStep;

    @Inject
    private AcceptRideSharedStep acceptRideSharedStep;

    @Inject
    private StartRideSharedStep startRideSharedStep;

    @Test
    void testC01ShouldStartARide() {
        var passengerId = UUID.randomUUID().toString();
        var driverId = UUID.randomUUID().toString();
        super.apiMockIntegration.mock(createAccountSearchMockData(passengerId, true));
        super.apiMockIntegration.mock(createAccountSearchMockData(driverId, false));
        var requestRideResponse = requestRideSharedStep.success(passengerId);
        acceptRideSharedStep.success(requestRideResponse.id(), driverId);
        startRideSharedStep.success(requestRideResponse.id());
    }

    @Test
    void testC02ShouldNotStartARideWithStatusInvalid() {
        var httpStatus = 422;
        var passengerId = UUID.randomUUID().toString();
        super.apiMockIntegration.mock(createAccountSearchMockData(passengerId, true));
        var requestRideResponse = requestRideSharedStep.success(passengerId);
        var response = startRideSharedStep.unsuccess(requestRideResponse.id(), httpStatus);
        assertEquals(httpStatus, response.status());
        assertEquals("POST", response.method());
        assertEquals(String.format(PATH, requestRideResponse.id()), response.path());
        assertEquals("validation.domain.exception.message", response.message());
        assertEquals(List.of("validation.invalid.ride.status.to.be.in.progress"), response.constraints());
    }

    @Test
    void testC03ShouldNotStartARideThatDoesNotExist() {
        var httpStatus = 404;
        var rideId = UUID.randomUUID().toString();
        var response = startRideSharedStep.unsuccess(rideId, httpStatus);
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