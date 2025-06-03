package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.sharedstep;

import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.exception.ExceptionRS;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.requestride.RequestRideRQ;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.requestride.RequestRideRS;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Named;

@Named
public final class RequestRideSharedStep {

    private static final String PATH = "/v1/rides";

    public RequestRideRS success(String passengerId) {
        var request = RequestRideRQ.builder()
                .passengerId(passengerId)
                .latitudeFrom(-23.529287790573242)
                .longitudeFrom(-46.675448474977)
                .latitudeTo(-24.908133115138398)
                .longitudeTo(-53.49481208223108)
                .build();
        return success(request);
    }

    public RequestRideRS success(RequestRideRQ request) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(PATH)
                .then()
                .statusCode(201)
                .extract()
                .as(RequestRideRS.class);
    }

    public ExceptionRS unsuccess(String passengerId, int httpStatus) {
        var request = RequestRideRQ.builder()
                .passengerId(passengerId)
                .latitudeFrom(-23.529287790573242)
                .longitudeFrom(-46.675448474977)
                .latitudeTo(-24.908133115138398)
                .longitudeTo(-53.49481208223108)
                .build();
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(PATH)
                .then()
                .statusCode(httpStatus)
                .extract()
                .as(ExceptionRS.class);
    }

}
