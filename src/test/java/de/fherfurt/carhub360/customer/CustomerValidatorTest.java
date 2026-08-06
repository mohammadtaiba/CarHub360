package de.fherfurt.carhub360.customer;

import java.util.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerValidatorTest {

    private final CustomerValidator validator = new CustomerValidator();

    @Test
    void validateAcceptsValidCustomer() {
        assertDoesNotThrow(() -> validator.validate(customer()));
    }

    @Test
    void validateRejectsMissingCustomer() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(null)
        );

        assertEquals("Customer payload is required.", exception.getMessage());
    }

    @Test
    void validateRejectsBlankFirstName() {
        Customer customer = customer();
        customer.setFirstName("  ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(customer)
        );

        assertEquals("firstName is required.", exception.getMessage());
    }

    @Test
    void validateRejectsBlankLastName() {
        Customer customer = customer();
        customer.setLastName("  ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(customer)
        );

        assertEquals("lastName is required.", exception.getMessage());
    }

    @Test
    void validateRejectsInvalidEmail() {
        Customer customer = customer();
        customer.setEmail("invalid-email");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(customer)
        );

        assertEquals("email must be a valid email address.", exception.getMessage());
    }

    @Test
    void validateRejectsMissingBirthdate() {
        Customer customer = customer();
        customer.setBirthdate(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(customer)
        );

        assertEquals("birthdate is required.", exception.getMessage());
    }

    private Customer customer() {
        Customer customer = new Customer();
        customer.setFirstName("Alex");
        customer.setLastName("Meyer");
        customer.setEmail("alex@example.com");
        customer.setBirthdate(new Date());
        return customer;
    }
}
