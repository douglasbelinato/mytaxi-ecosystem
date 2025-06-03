package br.com.mytaxi.infrastructure.interfaces.input.rest.controller.createaccount;

import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.createaccount.AccountCreatedRS;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.createaccount.CreateAccountRQ;
import br.com.mytaxi.infrastructure.interfaces.input.rest.dto.exception.ExceptionRS;
import br.com.mytaxi.infrastructure.config.BaseSpringTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateAccountControllerTest extends BaseSpringTest {

    private static final String PATH = "/v1/accounts";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateAccount() {
        var request = CreateAccountRQ.builder()
                .name("Fernanda")
                .surname("Silva")
                .email("fernanda@host.com")
                .cpf("525.118.173-65")
                .password("pass123")
                .isPassenger(true)
                .build();
        var responseEntity = restTemplate.postForEntity(PATH, request, AccountCreatedRS.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        var response = responseEntity.getBody();
        assertNotNull(response);
        assertDoesNotThrow(() -> UUID.fromString(response.id()));
    }

    @Test
    void testShouldNotCreateAccountWithAnExistingEmail() {
        var request = CreateAccountRQ.builder()
                .name("Fernanda")
                .surname("Silva")
                .email("fernanda2@host.com")
                .cpf("525.118.173-65")
                .password("pass123")
                .isPassenger(true)
                .build();
        var responseEntity = restTemplate.postForEntity(PATH, request, AccountCreatedRS.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        var exceptionResponseEntity = restTemplate.postForEntity(PATH, request, ExceptionRS.class);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exceptionResponseEntity.getStatusCode());
        var response = exceptionResponseEntity.getBody();
        assertNotNull(response);
        assertNotNull(response.constraints());
        assertEquals("Domain validation", response.message());
        assertEquals(1, response.constraints().size());
        assertEquals("This email is already in use. Please provide another one",
                response.constraints().getFirst());
    }

    @ParameterizedTest
    @MethodSource("testShouldNotCreateAccountWithConstraintsScenarios")
    void testShouldNotCreateAccountWithConstraints(CreateAccountRQ request, List<String> expectedConstraints) {
        var httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        var exceptionResponseEntity = restTemplate.postForEntity(PATH, request, ExceptionRS.class);
        assertEquals(httpStatus, exceptionResponseEntity.getStatusCode());
        var response = exceptionResponseEntity.getBody();
        assertNotNull(response);
        assertEquals(httpStatus.value(), response.status());
        assertEquals("POST", response.method());
        assertEquals(PATH, response.path());
        assertEquals("Domain validation", response.message());
        assertEquals(expectedConstraints, response.constraints());
    }

    public static Stream<Arguments> testShouldNotCreateAccountWithConstraintsScenarios() {
        var expectedConstraints = List.of("account.name=>must not be null",
                "account.surname=>must not be null",
                "account.email=>must not be null/empty",
                "account.cpf=>invalid cpf",
                "account.password=>must not be null",
                "isPassenger=>choose registration either as a passenger or a driver");
        return Stream.of(
                Arguments.of(CreateAccountRQ.builder().build(), expectedConstraints)
        );
    }

}
