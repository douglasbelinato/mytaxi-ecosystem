package br.com.mytaxi.integrationtests.cucumber.steps;

import br.com.mytaxi.integrationtests.cucumber.ScenarioContext;
import br.com.mytaxi.integrationtests.rest.sharedstep.StartRideSharedStep;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartRideSteps {

    @Inject
    private ScenarioContext scenarioContext;

    @Inject
    private StartRideSharedStep startRideSharedStep;

    @Quando("o motorista inicia a corrida")
    public void oMotoristaIniciaACorrida() {
        String rideId = scenarioContext.getRideId("");
        startRideSharedStep.success(rideId);
    }

    @Dado("o motorista iniciou a corrida")
    public void oMotoristaIniciouACorrida() {
        oMotoristaIniciaACorrida();
    }

    @Quando("o motorista tenta iniciar a corrida sem ter aceitado")
    public void oMotoristaTentaIniciarACorridaSemTerAceitado() {
        String rideId = scenarioContext.getRideId("");
        var response = startRideSharedStep.unsuccess(rideId, 422);
        scenarioContext.setExceptionResponse(response);
    }

    @Quando("o motorista tenta iniciar a corrida inexistente")
    public void oMotoristaTentaIniciarACorridaInexistente() {
        String rideId = scenarioContext.getRideId("");
        var response = startRideSharedStep.unsuccess(rideId, 404);
        scenarioContext.setExceptionResponse(response);
    }

    @Entao("a corrida deve ser iniciada com sucesso")
    public void aCorridaDeveSerIniciadaComSucesso() {
    }

    @Entao("o início deve falhar com status {int}")
    public void oInicioDeveFalharComStatus(int expectedStatus) {
        assertEquals(expectedStatus, scenarioContext.getExceptionResponse().status());
        assertEquals("POST", scenarioContext.getExceptionResponse().method());
        assertTrue(scenarioContext.getExceptionResponse().path().matches("/v1/rides/[a-f0-9\\-]+/start"));
    }

}
