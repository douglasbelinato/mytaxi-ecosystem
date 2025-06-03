package br.com.mytaxi.domain.common;

import br.com.mytaxi.domain.model.common.Cpf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CPFTest {

    @ParameterizedTest
    @MethodSource("testCreateValidCpfScenarios")
    void testCreateValidCpf(String expectedCpf, String expectedFormattedCpf) {
        var candidate = Cpf.create(expectedCpf);
        assertNotNull(candidate);
        assertNotNull(candidate.getValue());
        assertFalse(candidate.isNotValid());
        assertEquals(expectedCpf, candidate.getValue().getValue());
        assertEquals(expectedFormattedCpf, candidate.getValue().getFormatted());

    }

    public static Stream<Arguments> testCreateValidCpfScenarios() {
        return Stream.of(
                Arguments.of("628.656.092-00", "628.656.092-00"),
                Arguments.of("65218694428", "652.186.944-28")
        );
    }

    @ParameterizedTest
    @MethodSource("testCreateInvalidCpfScenarios")
    void testCreateInvalidCpf(String name, String expectedConstraint) {
        var candidate = Cpf.create(name);
        assertNotNull(candidate);
        assertNotNull(candidate.getConstraints());
        assertNull(candidate.getValue());
        assertTrue(candidate.isNotValid());
        assertEquals("cpf=>" + expectedConstraint, candidate.getConstraints().getAllInline());
    }

    public static Stream<Arguments> testCreateInvalidCpfScenarios() {
        return Stream.of(
                Arguments.of(null, "invalid cpf;"),
                Arguments.of("", "invalid cpf;"),
                Arguments.of("   ", "invalid cpf;"),
                Arguments.of("65218694429", "invalid cpf;"),
                Arguments.of("123", "invalid cpf;"),
                Arguments.of("6521869442911", "invalid cpf;")
        );
    }

    @Test
    void testCompareTwoNewValueObjects() {
        var candidate = Cpf.create("65218694428");
        var anotherCandidate = Cpf.create("65218694428");
        assertEquals(candidate.getValue(), anotherCandidate.getValue());
    }

}