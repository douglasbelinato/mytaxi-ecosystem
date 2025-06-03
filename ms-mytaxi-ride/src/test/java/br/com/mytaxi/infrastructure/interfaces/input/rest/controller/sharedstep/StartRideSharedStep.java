package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.sharedstep;

import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.exception.ExceptionRS;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Named;

@Named
public final class StartRideSharedStep {

    private static final String PATH = "/v1/rides/%s/start";

    public void success(String rideId) {
        RestAssured.when()
                .post(String.format(PATH, rideId))
                .then()
                .statusCode(204);
    }

    public ExceptionRS unsuccess(String rideId, int httpStatus) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .post(String.format(PATH, rideId))
                .then()
                .statusCode(httpStatus)
                .extract()
                .as(ExceptionRS.class);
    }

}
