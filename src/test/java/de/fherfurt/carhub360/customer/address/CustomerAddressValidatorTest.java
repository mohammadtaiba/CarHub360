package de.fherfurt.carhub360.customer.address;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerAddressValidatorTest {

    private final CustomerAddressValidator validator = new CustomerAddressValidator();

    @Test
    void validateAcceptsValidAddress() {
        assertDoesNotThrow(() -> validator.validate(address()));
    }

    @Test
    void validateRejectsMissingAddress() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(null)
        );

        assertEquals("Address payload is required.", exception.getMessage());
    }

    @Test
    void validateRejectsBlankCity() {
        CustomerAddress address = address();
        address.setCity("  ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(address)
        );

        assertEquals("city is required.", exception.getMessage());
    }

    @Test
    void validateRejectsBlankPostalCode() {
        CustomerAddress address = address();
        address.setPostalCode("  ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(address)
        );

        assertEquals("postalCode is required.", exception.getMessage());
    }

    @Test
    void validateRejectsBlankStreet() {
        CustomerAddress address = address();
        address.setStreet("  ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(address)
        );

        assertEquals("street is required.", exception.getMessage());
    }

    @Test
    void validateRejectsBlankStreetNumber() {
        CustomerAddress address = address();
        address.setStreetNumber("  ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(address)
        );

        assertEquals("streetNumber is required.", exception.getMessage());
    }

    private CustomerAddress address() {
        CustomerAddress address = new CustomerAddress();
        address.setCity("Erfurt");
        address.setPostalCode("99084");
        address.setStreet("Domplatz");
        address.setStreetNumber("1");
        return address;
    }
}
