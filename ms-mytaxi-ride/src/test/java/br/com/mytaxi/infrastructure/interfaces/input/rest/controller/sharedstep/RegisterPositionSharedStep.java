package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.sharedstep;

import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.registerposition.RegisterPositionRQ;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Named;

@Named
public class RegisterPositionSharedStep {

    private static final String PATH = "/v1/rides/%s/positions";

    public void success(String rideId, Double latitude, Double longitude) {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterPositionRQ.builder().latitude(latitude).longitude(longitude).build())
                .when()
                .post(String.format(PATH, rideId))
                .then()
                .statusCode(204);
    }

}
