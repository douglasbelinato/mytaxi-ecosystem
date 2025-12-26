package br.com.mytaxi.integrationtests.cucumber.steps;

import br.com.mytaxi.integrationtests.cucumber.ScenarioContext;
import br.com.mytaxi.integrationtests.rest.sharedstep.RequestRideSharedStep;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import jakarta.inject.Inject;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestRideSteps {

    @Inject
    private ScenarioContext scenarioContext;

    @Inject
    private RequestRideSharedStep requestRideSharedStep;

    @Quando("o passageiro solicita uma corrida")
    public void oPassageiroSolicitaUmaCorrida() {
        String passengerId = scenarioContext.getPassengerId("");
        var response = requestRideSharedStep.success(passengerId);
        scenarioContext.setRequestRideResponse(response);
        scenarioContext.setRideId("", response.id());
    }

    @Quando("o motorista tenta solicitar uma corrida")
    public void oMotoristaTentaSolicitarUmaCorrida() {
        String driverId = scenarioContext.getDriverId("");
        var response = requestRideSharedStep.unsuccess(driverId, 422);
        scenarioContext.setExceptionResponse(response);
    }

    @Dado("o passageiro já possui uma corrida solicitada")
    public void oPassageiroJaPossuiUmaCorridaSolicitada() {
        String passengerId = scenarioContext.getPassengerId("");
        var response = requestRideSharedStep.success(passengerId);
        scenarioContext.setRequestRideResponse(response);
        scenarioContext.setRideId("", response.id());
    }

    @Quando("o passageiro tenta solicitar outra corrida")
    public void oPassageiroTentaSolicitarOutraCorrida() {
        String passengerId = scenarioContext.getPassengerId("");
        var response = requestRideSharedStep.unsuccess(passengerId, 422);
        scenarioContext.setExceptionResponse(response);
    }

    @Entao("a corrida deve ser criada com sucesso")
    public void aCorridaDeveSerCriadaComSucesso() {
        assertEquals(true, scenarioContext.getRequestRideResponse() != null);
    }

    @Entao("o ID da corrida deve ser válido")
    public void oIdDaCorridaDeveSerValido() {
        assertDoesNotThrow(() -> UUID.fromString(scenarioContext.getRequestRideResponse().id()));
    }

    @Entao("a solicitação deve falhar com status {int}")
    public void aSolicitacaoDeveFalharComStatus(int expectedStatus) {
        assertEquals(expectedStatus, scenarioContext.getExceptionResponse().status());
        assertEquals("POST", scenarioContext.getExceptionResponse().method());
        assertEquals("/v1/rides", scenarioContext.getExceptionResponse().path());
        assertEquals("validation.domain.exception.message", scenarioContext.getExceptionResponse().message());
    }

}
