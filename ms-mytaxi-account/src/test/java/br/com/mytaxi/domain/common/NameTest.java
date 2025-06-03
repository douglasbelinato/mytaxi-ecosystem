package br.com.mytaxi.domain.common;

import br.com.mytaxi.domain.model.common.Name;
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

class NameTest {

    @ParameterizedTest
    @MethodSource("testCreateValidNameScenarios")
    void testCreateValidName(String expectedName) {
        var candidate = Name.create("name", expectedName, 2, 50);
        assertNotNull(candidate);
        assertNotNull(candidate.getValue());
        assertFalse(candidate.isNotValid());
        assertEquals(expectedName, candidate.getValue().getValue());
    }

    public static Stream<Arguments> testCreateValidNameScenarios() {
        return Stream.of(
                Arguments.of("Alicia Oliveira Silva"),
                Arguments.of("Alicia   Oliveira   Silva"),
                Arguments.of("Alicia"),
                Arguments.of("Oliveira Silva")
        );
    }

    @ParameterizedTest
    @MethodSource("testCreateInvalidNameScenarios")
    void testCreateInvalidName(String fieldName, String name, int minSize, int maxSize, String expectedConstraint) {
        var candidate = Name.create(fieldName, name, minSize, maxSize);
        assertNotNull(candidate);
        assertNotNull(candidate.getConstraints());
        assertNull(candidate.getValue());
        assertTrue(candidate.isNotValid());
        assertEquals(fieldName + "=>" + expectedConstraint, candidate.getConstraints().getAllInline());
    }

    public static Stream<Arguments> testCreateInvalidNameScenarios() {
        var expectedMinSize = 2;
        var expectedMaxSize = 20;
        return Stream.of(
                Arguments.of("fullName", null, expectedMinSize, expectedMaxSize, "must not be null/empty;"),
                Arguments.of("fullName", "", expectedMinSize, expectedMaxSize, "must not be null/empty;"),
                Arguments.of("fullName", "   ", expectedMinSize, expectedMaxSize, "must not be null/empty;"),
                Arguments.of("fullName", "João da Silva3", expectedMinSize, expectedMaxSize, "must have only letters;"),
                Arguments.of("fullName", "João_Silva", expectedMinSize, expectedMaxSize, "must have only letters;"),
                Arguments.of("fullName", "João Silva!", expectedMinSize, expectedMaxSize, "must have only letters;"),
                Arguments.of("fullName", "58943", expectedMinSize, expectedMaxSize, "must have only letters;"),
                Arguments.of("fullName", "A", expectedMinSize, expectedMaxSize,
                        "size invalid. It must be between " + expectedMinSize + " and " + expectedMaxSize + ";"),
                Arguments.of("fullName", "Maria Lucia Oliveira Marques", expectedMinSize, expectedMaxSize,
                        "size invalid. It must be between " + expectedMinSize + " and " + expectedMaxSize + ";")
        );
    }

    @Test
    void testCompareTwoNewValueObjects() {
        var fieldName = "fullName";
        var fullName = "Luana Oliveira";
        var minSize = 5;
        var maxSize = 50;
        var candidate = Name.create(fieldName, fullName, minSize, maxSize);
        var anotherCandidate = Name.create(fieldName, fullName, minSize, maxSize);
        assertEquals(candidate.getValue(), anotherCandidate.getValue());
    }

}