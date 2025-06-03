package br.com.mytaxi.domain.common;

import br.com.mytaxi.domain.model.common.CarPlate;
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

class CarPlateTest {

    @ParameterizedTest
    @MethodSource("testCreateValidCarPlateScenarios")
    void testCreateValidCarPlate(String value) {
        var candidate = CarPlate.create(value);
        assertNotNull(candidate);
        assertNotNull(candidate.getValue());
        assertFalse(candidate.isNotValid());
        assertEquals(value, candidate.getValue().getValue());
    }

    public static Stream<Arguments> testCreateValidCarPlateScenarios() {
        return Stream.of(
                Arguments.of("AAA1234"),
                Arguments.of("ABC-1234"),
                Arguments.of("BRA1C23")
        );
    }

    @ParameterizedTest
    @MethodSource("testCreateInvalidCarPlateScenarios")
    void testCreateInvalidCarPlate(String name, String expectedConstraint) {
        var candidate = CarPlate.create(name);
        assertNotNull(candidate);
        assertNotNull(candidate.getConstraints());
        assertNull(candidate.getValue());
        assertTrue(candidate.isNotValid());
        assertEquals("carPlate=>" + expectedConstraint, candidate.getConstraints().getAllInline());
    }

    public static Stream<Arguments> testCreateInvalidCarPlateScenarios() {
        return Stream.of(
                Arguments.of(null, "invalid car plate;"),
                Arguments.of("", "invalid car plate;"),
                Arguments.of("   ", "invalid car plate;"),
                Arguments.of("Any Text", "invalid car plate;"),
                Arguments.of("ABC123", "invalid car plate;")
        );
    }

    @Test
    void testCompareTwoNewValueObjects() {
        var candidate = CarPlate.create("ABC1234");
        var anotherCandidate = CarPlate.create("ABC1234");
        assertEquals(candidate.getValue(), anotherCandidate.getValue());
    }

}