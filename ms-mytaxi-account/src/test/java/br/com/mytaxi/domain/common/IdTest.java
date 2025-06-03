package br.com.mytaxi.domain.common;

import br.com.mytaxi.domain.model.common.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdTest {

    @Test
    void testCreateId() {
        var candidate = Id.create();
        assertNotNull(candidate);
        assertNotNull(candidate.getValue());
        assertFalse(candidate.isNotValid());
        assertDoesNotThrow(() -> UUID.fromString(candidate.getValue().getValue()));
    }

    @Test
    void testOfValidId() {
        var candidate = Id.of(UUID.randomUUID().toString());
        assertNotNull(candidate);
        assertNotNull(candidate.getValue());
        assertFalse(candidate.isNotValid());
        assertDoesNotThrow(() -> UUID.fromString(candidate.getValue().getValue()));
    }

    @ParameterizedTest
    @MethodSource("testOfInvalidIdScenarios")
    void testOfInvalidId(String value) {
        var candidate = Id.of(value);
        assertNotNull(candidate);
        assertNotNull(candidate.getConstraints());
        assertNull(candidate.getValue());
        assertTrue(candidate.isNotValid());
        assertEquals("id" + "=>invalid id;", candidate.getConstraints().getAllInline());
    }

    public static Stream<Arguments> testOfInvalidIdScenarios() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("123"),
                Arguments.of("ABC"),
                null
        );
    }

    @Test
    void testCompareTwoNewValueObjects() {
        var candidate = Id.create();
        var anotherCandidate = Id.create();
        assertNotEquals(candidate.getValue(), anotherCandidate.getValue());
    }

    @Test
    void testCompareTwoRetrievedValueObjects() {
        var id = UUID.randomUUID().toString();
        var candidate = Id.of(id);
        var anotherCandidate = Id.of(id);
        assertEquals(candidate.getValue(), anotherCandidate.getValue());
    }

}