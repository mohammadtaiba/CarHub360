package de.fherfurt.carhub360.vehicle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VehicleValidatorTest {

    private final VehicleValidator validator = new VehicleValidator();

    @Test
    void validateAcceptsValidVehicle() {
        assertDoesNotThrow(() -> validator.validate(vehicle()));
    }

    @Test
    void validateRejectsMissingVehicle() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(null)
        );

        assertEquals("Vehicle payload is required.", exception.getMessage());
    }

    @Test
    void validateRejectsBlankName() {
        Vehicle vehicle = vehicle();
        vehicle.setName("  ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(vehicle)
        );

        assertEquals("name is required.", exception.getMessage());
    }

    @Test
    void validateRejectsNegativeKilometerCount() {
        Vehicle vehicle = vehicle();
        vehicle.setKilometerCount(-1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(vehicle)
        );

        assertEquals("kilometerCount must not be negative.", exception.getMessage());
    }

    @Test
    void validateRejectsImplausibleConstructionYear() {
        Vehicle vehicle = vehicle();
        vehicle.setConstructionYear(1899);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(vehicle)
        );

        assertEquals("constructionYear must be plausible.", exception.getMessage());
    }

    private Vehicle vehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Octavia");
        vehicle.setBrand("Skoda");
        vehicle.setKilometerCount(42000);
        vehicle.setConstructionYear(2020);
        vehicle.setType("Combi");
        return vehicle;
    }
}
