package br.com.mytaxi.integrationtests.rest.sharedstep;

import br.com.mytaxi.infrastructure.input.rest.dto.acceptride.AcceptRideRQ;
import br.com.mytaxi.infrastructure.input.rest.dto.exception.ExceptionRS;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Named;

@Named
public final class AcceptRideSharedStep {

    private static final String PATH = "/v1/rides/%s/accept";

    public void success(String rideId, String driverId) {
        var acceptRideRequest = AcceptRideRQ.builder().driverId(driverId).build();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(acceptRideRequest)
                .when()
                .post(String.format(PATH, rideId))
                .then()
                .statusCode(204);
    }

    public ExceptionRS unsuccess(String rideId, String driverId, int httpStatus) {
        var acceptRideRequest = AcceptRideRQ.builder().driverId(driverId).build();
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(acceptRideRequest)
                .when()
                .post(String.format(PATH, rideId))
                .then()
                .statusCode(httpStatus)
                .extract()
                .as(ExceptionRS.class);
    }

}
