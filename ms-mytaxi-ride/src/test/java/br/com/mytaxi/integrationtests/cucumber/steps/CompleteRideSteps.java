package br.com.mytaxi.integrationtests.cucumber.steps;

import br.com.mytaxi.infrastructure.input.rest.dto.completeride.CompleteRideRQ;
import br.com.mytaxi.infrastructure.input.rest.dto.exception.ExceptionRS;
import br.com.mytaxi.integrationtests.cucumber.ScenarioContext;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompleteRideSteps {

    @Inject
    private ScenarioContext scenarioContext;

    @Quando("o motorista completa a corrida com token de cartão {string}")
    public void oMotoristaCompletaACorrida(String cardToken) {
        String rideId = scenarioContext.getRideId("");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(CompleteRideRQ.builder().creditCardToken(cardToken).build())
                .when()
                .post(String.format("/v1/rides/%s/complete", rideId))
                .then()
                .statusCode(204);
    }

    @Quando("o motorista tenta completar a corrida sem ter iniciado")
    public void oMotoristaTentaCompletarACorridaSemTerIniciado() {
        String rideId = scenarioContext.getRideId("");
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(CompleteRideRQ.builder().creditCardToken("card_1N3T00LkdIwHu7ixRdxpVI1Q").build())
                .when()
                .post(String.format("/v1/rides/%s/complete", rideId))
                .then()
                .statusCode(422)
                .extract()
                .as(ExceptionRS.class);
        scenarioContext.setExceptionResponse(response);
    }

    @Quando("o motorista tenta completar a corrida inexistente")
    public void oMotoristaTentaCompletarACorridaInexistente() {
        String rideId = scenarioContext.getRideId("");
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(CompleteRideRQ.builder().creditCardToken("card_1N3T00LkdIwHu7ixRdxpVI1Q").build())
                .when()
                .post(String.format("/v1/rides/%s/complete", rideId))
                .then()
                .statusCode(404)
                .extract()
                .as(ExceptionRS.class);
        scenarioContext.setExceptionResponse(response);
    }

    @Entao("a corrida deve ser completada com sucesso")
    public void aCorridaDeveSerCompletadaComSucesso() {
    }

    @Entao("a conclusão deve falhar com status {int}")
    public void aConclusaoDeveFalharComStatus(int expectedStatus) {
        assertEquals(expectedStatus, scenarioContext.getExceptionResponse().status());
        assertEquals("POST", scenarioContext.getExceptionResponse().method());
        assertTrue(scenarioContext.getExceptionResponse().path().matches("/v1/rides/[a-f0-9\\-]+/complete"));
    }

}
