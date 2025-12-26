package br.com.mytaxi.integrationtests.cucumber.steps;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.domain.model.ride.RideStatusEnum;
import br.com.mytaxi.infrastructure.input.rest.dto.exception.ExceptionRS;
import br.com.mytaxi.infrastructure.input.rest.dto.requestride.RequestRideRQ;
import br.com.mytaxi.infrastructure.input.rest.dto.searchride.RideRS;
import br.com.mytaxi.integrationtests.config.apimock.ApiMockIntegration;
import br.com.mytaxi.integrationtests.config.apimock.data.accountsearch.AccountSearchApiMockData;
import br.com.mytaxi.integrationtests.cucumber.ScenarioContext;
import br.com.mytaxi.integrationtests.rest.sharedstep.RequestRideSharedStep;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SearchRideSteps {

    @Inject
    private ScenarioContext scenarioContext;

    @Inject
    private RequestRideSharedStep requestRideSharedStep;

    @Inject
    private ApiMockIntegration apiMockIntegration;

    private RideRS searchResponse;

    @Dado("o mock de busca de conta retorna os dados:")
    public void oMockDeBuscaDeContaRetornaOsDados(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = rows.get(0);

        String passengerId = scenarioContext.getPassengerId("");
        boolean isPassenger = Boolean.parseBoolean(row.get("isPassenger"));

        apiMockIntegration.mock(AccountSearchApiMockData.builder()
                .account(AccountDTO.builder()
                        .id(passengerId)
                        .name(row.get("nome"))
                        .surname(row.get("sobrenome"))
                        .email(row.get("email"))
                        .isPassenger(isPassenger)
                        .isDriver(!isPassenger)
                        .build())
                .build());
    }

    @Dado("existe uma corrida solicitada pelo passageiro com:")
    public void existeUmaCorridaSolicitadaPeloPassageiroCom(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = rows.get(0);

        String passengerId = scenarioContext.getPassengerId("");
        var request = RequestRideRQ.builder()
                .passengerId(passengerId)
                .latitudeFrom(Double.parseDouble(row.get("latitudeFrom")))
                .longitudeFrom(Double.parseDouble(row.get("longitudeFrom")))
                .latitudeTo(Double.parseDouble(row.get("latitudeTo")))
                .longitudeTo(Double.parseDouble(row.get("longitudeTo")))
                .build();

        var response = requestRideSharedStep.success(request);
        scenarioContext.setRequestRideResponse(response);
        scenarioContext.setRideId("", response.id());
    }

    @Quando("eu busco a corrida pelo ID")
    public void euBuscoACorridaPeloId() {
        String rideId = scenarioContext.getRideId("");
        searchResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/rides/" + rideId)
                .then()
                .statusCode(200)
                .extract()
                .as(RideRS.class);
    }

    @Quando("eu busco a corrida inexistente pelo ID")
    public void euBuscoACorridaInexistentePeloId() {
        String rideId = scenarioContext.getRideId("");
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/rides/" + rideId)
                .then()
                .statusCode(404)
                .extract()
                .as(ExceptionRS.class);
        scenarioContext.setExceptionResponse(response);
    }

    @Entao("a busca deve retornar sucesso com status {int}")
    public void aBuscaDeveRetornarSucessoComStatus(int expectedStatus) {
        assertNotNull(searchResponse);
    }

    @Entao("os detalhes da corrida devem conter:")
    public void osDetalhesDaCorridaDevemConter(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            String campo = row.get("campo");
            String valor = row.get("valor");

            switch (campo) {
                case "passengerId":
                    assertEquals(scenarioContext.getPassengerId(""), searchResponse.passengerId());
                    break;
                case "passengerName":
                    assertEquals(valor, searchResponse.passengerName());
                    break;
                case "passengerSurname":
                    assertEquals(valor, searchResponse.passengerSurname());
                    break;
                case "passengerEmail":
                    assertEquals(valor, searchResponse.passengerEmail());
                    break;
                case "status":
                    assertEquals(RideStatusEnum.REQUESTED.name(), searchResponse.status());
                    break;
                case "driverId":
                    assertNull(searchResponse.driverId());
                    break;
            }
        }
    }

    @Entao("a busca deve falhar com status {int}")
    public void aBuscaDeveFalharComStatus(int expectedStatus) {
        String rideId = scenarioContext.getRideId("");
        assertEquals(expectedStatus, scenarioContext.getExceptionResponse().status());
        assertEquals("GET", scenarioContext.getExceptionResponse().method());
        assertEquals("/v1/rides/" + rideId, scenarioContext.getExceptionResponse().path());
    }

}
