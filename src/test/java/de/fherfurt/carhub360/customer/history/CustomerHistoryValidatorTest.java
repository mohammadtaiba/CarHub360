package de.fherfurt.carhub360.customer.history;

import java.util.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerHistoryValidatorTest {

    private final CustomerHistoryValidator validator = new CustomerHistoryValidator();

    @Test
    void validateAcceptsValidCustomerHistoryData() {
        assertDoesNotThrow(() -> validator.validate(
                CustomerHistoryReview.FUENF,
                "Successful vehicle handover",
                new Date()
        ));
    }

    @Test
    void validateRejectsMissingReview() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(null, "Successful vehicle handover", new Date())
        );

        assertEquals("customerHistoryReview is required.", exception.getMessage());
    }

    @Test
    void validateRejectsBlankDescription() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(CustomerHistoryReview.FUENF, "  ", new Date())
        );

        assertEquals("description is required.", exception.getMessage());
    }

    @Test
    void validateRejectsMissingActionDate() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(CustomerHistoryReview.FUENF, "Successful vehicle handover", null)
        );

        assertEquals("actionDate is required.", exception.getMessage());
    }
}
