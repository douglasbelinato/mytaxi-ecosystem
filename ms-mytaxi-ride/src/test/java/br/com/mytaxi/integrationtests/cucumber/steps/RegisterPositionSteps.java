package br.com.mytaxi.integrationtests.cucumber.steps;

import br.com.mytaxi.infrastructure.input.rest.dto.exception.ExceptionRS;
import br.com.mytaxi.infrastructure.input.rest.dto.registerposition.RegisterPositionRQ;
import br.com.mytaxi.integrationtests.cucumber.ScenarioContext;
import br.com.mytaxi.integrationtests.rest.sharedstep.RegisterPositionSharedStep;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterPositionSteps {

    @Inject
    private ScenarioContext scenarioContext;

    @Inject
    private RegisterPositionSharedStep registerPositionSharedStep;

    @Quando("o motorista registra a posição com latitude {string} e longitude {string}")
    public void oMotoristaRegistraAPosicao(String latitude, String longitude) {
        String rideId = scenarioContext.getRideId("");
        registerPositionSharedStep.success(rideId, Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

    @Dado("o motorista registrou as seguintes posições:")
    public void oMotoristaRegistrouAsSeguintesPosicoes(io.cucumber.datatable.DataTable dataTable) {
        String rideId = scenarioContext.getRideId("");
        List<List<String>> rows = dataTable.asLists(String.class);
        for (int i = 1; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            Double latitude = Double.parseDouble(row.get(0));
            Double longitude = Double.parseDouble(row.get(1));
            registerPositionSharedStep.success(rideId, latitude, longitude);
        }
    }

    @Quando("o motorista tenta registrar a posição com latitude {string} e longitude {string}")
    public void oMotoristaTentaRegistrarAPosicao(String latitude, String longitude) {
        String rideId = scenarioContext.getRideId("");
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterPositionRQ.builder()
                        .latitude(Double.parseDouble(latitude))
                        .longitude(Double.parseDouble(longitude))
                        .build())
                .when()
                .post(String.format("/v1/rides/%s/positions", rideId))
                .then()
                .statusCode(422)
                .extract()
                .as(ExceptionRS.class);
        scenarioContext.setExceptionResponse(response);
    }

    @Quando("o motorista tenta registrar a posição sem ter iniciado a corrida")
    public void oMotoristaTentaRegistrarAPosicaoSemTerIniciadoACorrida() {
        String rideId = scenarioContext.getRideId("");
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterPositionRQ.builder()
                        .latitude(-24.955282)
                        .longitude(-53.459054)
                        .build())
                .when()
                .post(String.format("/v1/rides/%s/positions", rideId))
                .then()
                .statusCode(422)
                .extract()
                .as(ExceptionRS.class);
        scenarioContext.setExceptionResponse(response);
    }

    @Quando("o motorista tenta registrar a posição na corrida inexistente")
    public void oMotoristaTentaRegistrarAPosicaoNaCorridaInexistente() {
        String rideId = scenarioContext.getRideId("");
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(RegisterPositionRQ.builder()
                        .latitude(-24.955282)
                        .longitude(-53.459054)
                        .build())
                .when()
                .post(String.format("/v1/rides/%s/positions", rideId))
                .then()
                .statusCode(404)
                .extract()
                .as(ExceptionRS.class);
        scenarioContext.setExceptionResponse(response);
    }

    @Entao("a posição deve ser registrada com sucesso")
    public void aPosicaoDeveSerRegistradaComSucesso() {
    }

    @Entao("o registro deve falhar com status {int}")
    public void oRegistroDeveFalharComStatus(int expectedStatus) {
        assertEquals(expectedStatus, scenarioContext.getExceptionResponse().status());
        assertEquals("POST", scenarioContext.getExceptionResponse().method());
        assertTrue(scenarioContext.getExceptionResponse().path().matches("/v1/rides/[a-f0-9\\-]+/positions"));
    }

}
