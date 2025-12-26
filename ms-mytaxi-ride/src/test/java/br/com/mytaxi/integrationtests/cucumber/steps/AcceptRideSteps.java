package br.com.mytaxi.integrationtests.cucumber.steps;

import br.com.mytaxi.integrationtests.cucumber.ScenarioContext;
import br.com.mytaxi.integrationtests.rest.sharedstep.AcceptRideSharedStep;
import br.com.mytaxi.integrationtests.rest.sharedstep.RequestRideSharedStep;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AcceptRideSteps {

    @Inject
    private ScenarioContext scenarioContext;

    @Inject
    private RequestRideSharedStep requestRideSharedStep;

    @Inject
    private AcceptRideSharedStep acceptRideSharedStep;

    @Dado("existe uma corrida solicitada pelo passageiro")
    public void existeUmaCorridaSolicitadaPeloPassageiro() {
        String passengerId = scenarioContext.getPassengerId("");
        var response = requestRideSharedStep.success(passengerId);
        scenarioContext.setRequestRideResponse(response);
        scenarioContext.setRideId("", response.id());
    }

    @Dado("existe uma corrida solicitada pelo passageiro {string}")
    public void existeUmaCorridaSolicitadaPeloPassageiro(String identifier) {
        String passengerId = scenarioContext.getPassengerId(identifier);
        var response = requestRideSharedStep.success(passengerId);
        scenarioContext.setRideId(identifier, response.id());
    }

    @Quando("o motorista aceita a corrida")
    public void oMotoristaAceitaACorrida() {
        String rideId = scenarioContext.getRideId("");
        String driverId = scenarioContext.getDriverId("");
        acceptRideSharedStep.success(rideId, driverId);
    }

    @Dado("o motorista aceitou a corrida")
    public void oMotoristaAceitouACorrida() {
        oMotoristaAceitaACorrida();
    }

    @Dado("o motorista já aceitou a corrida do passageiro {string}")
    public void oMotoristaJaAceitouACorridaDoPassageiro(String identifier) {
        String rideId = scenarioContext.getRideId(identifier);
        String driverId = scenarioContext.getDriverId("");
        acceptRideSharedStep.success(rideId, driverId);
    }

    @Dado("o motorista {string} já aceitou a corrida")
    public void oMotoristaJaAceitouACorrida(String driverIdentifier) {
        String rideId = scenarioContext.getRideId("");
        String driverId = scenarioContext.getDriverId(driverIdentifier);
        acceptRideSharedStep.success(rideId, driverId);
    }

    @Quando("o passageiro tenta aceitar a corrida")
    public void oPassageiroTentaAceitarACorrida() {
        String rideId = scenarioContext.getRideId("");
        String passengerId = scenarioContext.getPassengerId("");
        var response = acceptRideSharedStep.unsuccess(rideId, passengerId, 422);
        scenarioContext.setExceptionResponse(response);
    }

    @Quando("o motorista tenta aceitar a corrida do passageiro {string}")
    public void oMotoristaTentaAceitarACorridaDoPassageiro(String identifier) {
        String rideId = scenarioContext.getRideId(identifier);
        String driverId = scenarioContext.getDriverId("");
        var response = acceptRideSharedStep.unsuccess(rideId, driverId, 422);
        scenarioContext.setExceptionResponse(response);
    }

    @Quando("o motorista {string} tenta aceitar a mesma corrida")
    public void oMotoristaTentaAceitarAMesmaCorrida(String driverIdentifier) {
        String rideId = scenarioContext.getRideId("");
        String driverId = scenarioContext.getDriverId(driverIdentifier);
        var response = acceptRideSharedStep.unsuccess(rideId, driverId, 422);
        scenarioContext.setExceptionResponse(response);
    }

    @Quando("o motorista tenta aceitar a corrida inexistente")
    public void oMotoristaTentaAceitarACorridaInexistente() {
        String rideId = scenarioContext.getRideId("");
        String driverId = scenarioContext.getDriverId("");
        var response = acceptRideSharedStep.unsuccess(rideId, driverId, 404);
        scenarioContext.setExceptionResponse(response);
    }

    @Entao("a corrida deve ser aceita com sucesso")
    public void aCorridaDeveSerAceitaComSucesso() {
    }

    @Entao("a aceitação deve falhar com status {int}")
    public void aAceitacaoDeveFalharComStatus(int expectedStatus) {
        assertEquals(expectedStatus, scenarioContext.getExceptionResponse().status());
        assertEquals("POST", scenarioContext.getExceptionResponse().method());
        assertTrue(scenarioContext.getExceptionResponse().path().matches("/v1/rides/[a-f0-9\\-]+/accept"));
    }

}
