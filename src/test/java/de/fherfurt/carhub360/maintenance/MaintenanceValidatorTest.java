package de.fherfurt.carhub360.maintenance;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MaintenanceValidatorTest {

    private final MaintenanceValidator validator = new MaintenanceValidator();

    @Test
    void validateAcceptsValidMaintenanceData() {
        assertDoesNotThrow(() -> validator.validate(
                new Date(),
                null,
                BigDecimal.valueOf(99.99),
                "Oil service"
        ));
    }

    @Test
    void validateRejectsMissingStartDate() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(null, null, BigDecimal.valueOf(99.99), "Oil service")
        );

        assertEquals("maintenanceStartDate is required.", exception.getMessage());
    }

    @Test
    void validateRejectsEndDateBeforeStartDate() {
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() - 1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(startDate, endDate, BigDecimal.valueOf(99.99), "Oil service")
        );

        assertEquals("maintenanceEndDate must not be before maintenanceStartDate.", exception.getMessage());
    }

    @Test
    void validateRejectsNegativeCost() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(new Date(), null, BigDecimal.valueOf(-1), "Oil service")
        );

        assertEquals("maintenanceCost must not be negative.", exception.getMessage());
    }

    @Test
    void validateRejectsBlankDescription() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(new Date(), null, BigDecimal.ZERO, "  ")
        );

        assertEquals("maintenanceDescription is required.", exception.getMessage());
    }
}
