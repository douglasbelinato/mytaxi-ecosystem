package br.com.mytaxi.domain.common;

import br.com.mytaxi.domain.model.common.Email;
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

class EmailTest {

    @ParameterizedTest
    @MethodSource("testCreateValidEmailScenarios")
    void testCreateValidEmail(String expectedEmail) {
        var candidate = Email.create(expectedEmail, 2, 50);
        assertNotNull(candidate);
        assertNotNull(candidate.getValue());
        assertFalse(candidate.isNotValid());
        assertEquals(expectedEmail, candidate.getValue().getValue());
    }

    public static Stream<Arguments> testCreateValidEmailScenarios() {
        return Stream.of(
                Arguments.of("alicia@gmail.com"),
                Arguments.of("alicia45@gmail.com"),
                Arguments.of("alicia_silva@uol.com.br"),
                Arguments.of("045alicia_silva@uol.com.br")
        );
    }

    @ParameterizedTest
    @MethodSource("testCreateInvalidEmailScenarios")
    void testCreateInvalidEmail(String email, int minSize, int maxSize, String expectedConstraint) {
        var candidate = Email.create(email, minSize, maxSize);
        assertNotNull(candidate);
        assertNotNull(candidate.getConstraints());
        assertNull(candidate.getValue());
        assertTrue(candidate.isNotValid());
        assertEquals("email=>" + expectedConstraint, candidate.getConstraints().getAllInline());
    }

    public static Stream<Arguments> testCreateInvalidEmailScenarios() {
        var expectedMinSize = 12;
        var expectedMaxSize = 25;
        return Stream.of(
                Arguments.of(null, expectedMinSize, expectedMaxSize, "must not be null/empty;"),
                Arguments.of("", expectedMinSize, expectedMaxSize, "must not be null/empty;"),
                Arguments.of("   ", expectedMinSize, expectedMaxSize, "must not be null/empty;"),
                Arguments.of("alicia@corp", expectedMinSize, expectedMaxSize, "invalid email;"),
                Arguments.of("!alicia@corp.com", expectedMinSize, expectedMaxSize, "invalid email;"),
                Arguments.of("JoãoSilva.com", expectedMinSize, expectedMaxSize, "invalid email;"),
                Arguments.of("alicia$@corp.com", expectedMinSize, expectedMaxSize, "invalid email;"),
                Arguments.of("al@corp.edu", expectedMinSize, expectedMaxSize,
                        "size invalid. It must be between " + expectedMinSize + " and " + expectedMaxSize + ";"),
                Arguments.of("aliciasilvabragaorlando@corp.com", expectedMinSize, expectedMaxSize,
                        "size invalid. It must be between " + expectedMinSize + " and " + expectedMaxSize + ";")
        );
    }

    @Test
    void testCompareTwoNewValueObjects() {
        var minSize = 12;
        var maxSize = 25;
        var candidate = Email.create("luke@corp.com.br", minSize, maxSize);
        var anotherCandidate = Email.create("luke@corp.com.br", minSize, maxSize);
        assertEquals(candidate.getValue(), anotherCandidate.getValue());
    }

}