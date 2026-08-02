package de.fherfurt.carhub360.shared.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequiredTextTest {

    @Test
    void requireAcceptsNonBlankText() {
        assertDoesNotThrow(() -> RequiredText.require("Golf", "name"));
    }

    @Test
    void requireRejectsNullText() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> RequiredText.require(null, "name")
        );

        assertEquals("name is required.", exception.getMessage());
    }

    @Test
    void requireRejectsBlankText() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> RequiredText.require("  ", "name")
        );

        assertEquals("name is required.", exception.getMessage());
    }
}
