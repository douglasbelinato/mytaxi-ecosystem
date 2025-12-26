package br.com.mytaxi.integrationtests.cucumber.steps;

import br.com.mytaxi.application.output.rest.dto.account.AccountDTO;
import br.com.mytaxi.integrationtests.config.apimock.ApiMockIntegration;
import br.com.mytaxi.integrationtests.config.apimock.data.ApiMockTypeEnum;
import br.com.mytaxi.integrationtests.config.apimock.data.accountsearch.AccountSearchApiMockData;
import br.com.mytaxi.integrationtests.cucumber.ScenarioContext;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import jakarta.inject.Inject;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommonSteps {

    @Inject
    private ScenarioContext scenarioContext;

    @Inject
    private ApiMockIntegration apiMockIntegration;

    @Dado("que o serviço de corridas está disponível")
    public void queOServicoDeCorridasEstaDisponivel() {
    }

    @Dado("que existe um passageiro com ID gerado")
    public void queExisteUmPassageiroComIdGerado() {
        String passengerId = UUID.randomUUID().toString();
        scenarioContext.setPassengerId("", passengerId);
    }

    @Dado("que existe um passageiro {string} com ID gerado")
    public void queExisteUmPassageiroComIdGerado(String identifier) {
        String passengerId = UUID.randomUUID().toString();
        scenarioContext.setPassengerId(identifier, passengerId);
    }

    @Dado("que existe um motorista com ID gerado")
    public void queExisteUmMotoristaComIdGerado() {
        String driverId = UUID.randomUUID().toString();
        scenarioContext.setDriverId("", driverId);
    }

    @Dado("que existe um motorista {string} com ID gerado")
    public void queExisteUmMotoristaComIdGerado(String identifier) {
        String driverId = UUID.randomUUID().toString();
        scenarioContext.setDriverId(identifier, driverId);
    }

    @Dado("o mock de busca de conta está configurado para o passageiro")
    public void oMockDeBuscaDeContaEstaConfiguradoParaOPassageiro() {
        String passengerId = scenarioContext.getPassengerId("");
        apiMockIntegration.mock(createAccountSearchMockData(passengerId, true));
    }

    @Dado("o mock de busca de conta está configurado para o passageiro {string}")
    public void oMockDeBuscaDeContaEstaConfiguradoParaOPassageiro(String identifier) {
        String passengerId = scenarioContext.getPassengerId(identifier);
        apiMockIntegration.mock(createAccountSearchMockData(passengerId, true));
    }

    @Dado("o mock de busca de conta está configurado para o motorista")
    public void oMockDeBuscaDeContaEstaConfiguradoParaOMotorista() {
        String driverId = scenarioContext.getDriverId("");
        apiMockIntegration.mock(createAccountSearchMockData(driverId, false));
    }

    @Dado("o mock de busca de conta está configurado para o motorista {string}")
    public void oMockDeBuscaDeContaEstaConfiguradoParaOMotorista(String identifier) {
        String driverId = scenarioContext.getDriverId(identifier);
        apiMockIntegration.mock(createAccountSearchMockData(driverId, false));
    }

    @Dado("o mock de pagamento está configurado")
    public void oMockDePagamentoEstaConfigurado() {
        apiMockIntegration.mock(ApiMockTypeEnum.PAYMENT);
    }

    @Dado("que existe um ID de corrida inexistente")
    public void queExisteUmIdDeCorridaInexistente() {
        String nonExistentRideId = UUID.randomUUID().toString();
        scenarioContext.setRideId("", nonExistentRideId);
    }

    @Dado("existe um ID de corrida inexistente")
    public void existeUmIdDeCorridaInexistente() {
        queExisteUmIdDeCorridaInexistente();
    }

    @Dado("que existe um ID de corrida qualquer")
    public void queExisteUmIdDeCorridaQualquer() {
        String rideId = UUID.randomUUID().toString();
        scenarioContext.setRideId("", rideId);
    }

    @Entao("a mensagem de erro deve ser {string}")
    public void aMensagemDeErroDeveSer(String expectedMessage) {
        assertNotNull(scenarioContext.getExceptionResponse());
        List<String> constraints = scenarioContext.getExceptionResponse().constraints();
        if (constraints == null || constraints.isEmpty()) {
            assertEquals(expectedMessage, scenarioContext.getExceptionResponse().message());
        } else {
            assertEquals(List.of(expectedMessage), constraints);
        }
    }

    @Entao("a mensagem de erro deve conter {string}")
    public void aMensagemDeErroDeveConter(String expectedMessagePart) {
        assertNotNull(scenarioContext.getExceptionResponse());
        List<String> constraints = scenarioContext.getExceptionResponse().constraints();
        assertNotNull(constraints);
        assertEquals(1, constraints.size());
        assertEquals(true, constraints.get(0).contains(expectedMessagePart));
    }

    @Entao("as restrições devem estar vazias")
    public void asRestricoesDevemEstarVazias() {
        assertNotNull(scenarioContext.getExceptionResponse());
        assertEquals(Collections.emptyList(), scenarioContext.getExceptionResponse().constraints());
    }

    private AccountSearchApiMockData createAccountSearchMockData(String id, boolean isPassenger) {
        return AccountSearchApiMockData.builder()
                .account(AccountDTO.builder()
                        .id(id)
                        .name("Alice")
                        .surname("Silva")
                        .email("alice@host.com.br")
                        .isPassenger(isPassenger)
                        .isDriver(!isPassenger)
                        .build())
                .build();
    }

}
