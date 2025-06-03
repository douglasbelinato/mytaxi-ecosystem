package br.com.mytaxi.domain.account;

import br.com.mytaxi.domain.model.account.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountTest {

    @ParameterizedTest
    @MethodSource("testCreateValidAccountScenarios")
    void testCreateValidAccount(String name, String surname, String email, String cpf, String carPlate,
                                String password, boolean isPassenger, boolean isDriver, String expectedCarPlate) {
        var candidate = Account.create(name, surname, email, cpf, carPlate, password, isPassenger, isDriver);
        assertNotNull(candidate);
        assertNotNull(candidate.getValue());
        assertFalse(candidate.isNotValid());
        assertEquals(name, candidate.getValue().getName().getValue());
        assertEquals(surname, candidate.getValue().getSurname().getValue());
        assertEquals(email, candidate.getValue().getEmail().getValue());
        assertEquals(cpf, candidate.getValue().getCpf().getValue());
        assertEquals(expectedCarPlate, isDriver ? candidate.getValue().getCarPlate().getValue() : null);
        assertEquals(password, candidate.getValue().getPassword().getValue());
        assertEquals(isPassenger, candidate.getValue().isPassenger());
        assertEquals(isDriver, candidate.getValue().isDriver());
    }

    public static Stream<Arguments> testCreateValidAccountScenarios() {
        return Stream.of(
                Arguments.of("Maria", "Souza", "maria@corp.com", "553.276.681-93", "SBB-1050", "pass123",
                        true, false, null),
                Arguments.of("Caio", "M. Oliveira", "caio@uol.com.br", "27272743190", "BRA8H55", "pass123",
                        false, true, "BRA8H55")
        );
    }

    @Test
    void testCompareTwoNewEntities() {
        var candidate = Account.create("Maria", "Souza", "maria@corp.com", "553.276.681-93", "SBB-1050", "pass123",
                true, false);
        var anotherCandidate = Account.create("Caio", "M. Oliveira", "caio@uol.com.br", "27272743190", "BRA8H55", "pass123",
                false, true);
        assertNotEquals(candidate.getValue(), anotherCandidate.getValue());
    }

}