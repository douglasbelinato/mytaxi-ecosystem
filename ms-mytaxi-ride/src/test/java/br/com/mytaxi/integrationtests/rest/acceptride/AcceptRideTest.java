package br.com.mytaxi.integrationtests.rest.acceptride;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.integrationtests.config.BaseTest;
import br.com.mytaxi.integrationtests.config.apimock.data.accountsearch.AccountSearchApiMockData;
import br.com.mytaxi.integrationtests.rest.sharedstep.AcceptRideSharedStep;
import br.com.mytaxi.integrationtests.rest.sharedstep.RequestRideSharedStep;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AcceptRideTest extends BaseTest {

    private static final String PATH = "/v1/rides/%s/accept";

    @Inject
    private RequestRideSharedStep requestRideSharedStep;

    @Inject
    private AcceptRideSharedStep acceptRideSharedStep;

    @Test
    void testC01ShouldAcceptARide() {
        var passengerId = UUID.randomUUID().toString();
        var driverId = UUID.randomUUID().toString();
        var passengerMockData = createAccountSearchMockData(passengerId, true);
        var driverMockData = createAccountSearchMockData(driverId, false);
        super.apiMockIntegration.mock(passengerMockData);
        super.apiMockIntegration.mock(driverMockData);
        var requestRideResponse = requestRideSharedStep.success(passengerId);
        acceptRideSharedStep.success(requestRideResponse.id(), driverId);
    }

    @Test
    void testC02ShouldNotAcceptARideWhenIdIsNotFromADriver() {
        var httpStatus = 422;
        var passengerId = UUID.randomUUID().toString();
        var passengerMockData = createAccountSearchMockData(passengerId, true);
        super.apiMockIntegration.mock(passengerMockData);
        var requestRideResponse = requestRideSharedStep.success(passengerId);
        var response = acceptRideSharedStep.unsuccess(requestRideResponse.id(), passengerId, httpStatus);
        assertEquals(httpStatus, response.status());
        assertEquals("POST", response.method());
        assertEquals(String.format(PATH, requestRideResponse.id()), response.path());
        assertEquals("validation.domain.exception.message", response.message());
        assertEquals(List.of("validation.ride.account.do.not.belong.to.a.driver"), response.constraints());
    }

    @Test
    void testC03ShouldNotAcceptARideForDriverWithOpenedRide() {
        var httpStatus = 422;
        var passengerAId = UUID.randomUUID().toString();
        var passengerBId = UUID.randomUUID().toString();
        var driverId = UUID.randomUUID().toString();
        super.apiMockIntegration.mock(createAccountSearchMockData(passengerAId, true));
        super.apiMockIntegration.mock(createAccountSearchMockData(passengerBId, true));
        super.apiMockIntegration.mock(createAccountSearchMockData(driverId, false));
        var requestRideForPassengerAResponse = requestRideSharedStep.success(passengerAId);
        acceptRideSharedStep.success(requestRideForPassengerAResponse.id(), driverId);
        var requestRideForPassengerBResponse = requestRideSharedStep.success(passengerBId);
        var response = acceptRideSharedStep.unsuccess(requestRideForPassengerBResponse.id(), driverId, httpStatus);
        assertEquals(httpStatus, response.status());
        assertEquals("POST", response.method());
        assertEquals(String.format(PATH, requestRideForPassengerBResponse.id()), response.path());
        assertEquals("validation.domain.exception.message", response.message());
        assertEquals(List.of("validation.driver.already.has.a.ride.assigned"), response.constraints());
    }

    @Test
    void testC04ShouldNotAcceptARideWithStatusInvalid() {
        var httpStatus = 422;
        var passengerId = UUID.randomUUID().toString();
        var driverAId = UUID.randomUUID().toString();
        var driverBId = UUID.randomUUID().toString();
        super.apiMockIntegration.mock(createAccountSearchMockData(passengerId, true));
        super.apiMockIntegration.mock(createAccountSearchMockData(driverAId, false));
        super.apiMockIntegration.mock(createAccountSearchMockData(driverBId, false));
        var requestRideResponse = requestRideSharedStep.success(passengerId);
        acceptRideSharedStep.success(requestRideResponse.id(), driverAId);
        var response = acceptRideSharedStep.unsuccess(requestRideResponse.id(), driverBId, httpStatus);
        assertEquals(httpStatus, response.status());
        assertEquals("POST", response.method());
        assertEquals(String.format(PATH, requestRideResponse.id()), response.path());
        assertEquals("validation.domain.exception.message", response.message());
        assertEquals(List.of("validation.invalid.ride.status.to.be.accepted"), response.constraints());
    }

    @Test
    void testC05ShouldNotAcceptARideThatDoesNotExist() {
        var httpStatus = 404;
        var rideId = UUID.randomUUID().toString();
        var response = acceptRideSharedStep.unsuccess(rideId, UUID.randomUUID().toString(), httpStatus);
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