package de.fherfurt.carhub360.vehicle.rent;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RentVehicleValidatorTest {

    private final RentVehicleValidator validator = new RentVehicleValidator();

    @Test
    void validateAcceptsValidRentVehicle() {
        assertDoesNotThrow(() -> validator.validate(rentVehicle()));
    }

    @Test
    void validateRejectsMissingRentVehicle() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(null)
        );

        assertEquals("Rent vehicle payload is required.", exception.getMessage());
    }

    @Test
    void validateRejectsMissingDailyPrice() {
        RentVehicle vehicle = rentVehicle();
        vehicle.setDailyPrice(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(vehicle)
        );

        assertEquals("dailyPrice must be greater than zero.", exception.getMessage());
    }

    @Test
    void validateRejectsNegativeDeposit() {
        RentVehicle vehicle = rentVehicle();
        vehicle.setDeposit(BigDecimal.valueOf(-1));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(vehicle)
        );

        assertEquals("deposit must not be negative.", exception.getMessage());
    }

    @Test
    void validateRejectsBlankLicensePlate() {
        RentVehicle vehicle = rentVehicle();
        vehicle.setLicensePlate("  ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(vehicle)
        );

        assertEquals("licensePlate is required.", exception.getMessage());
    }

    private RentVehicle rentVehicle() {
        RentVehicle vehicle = new RentVehicle();
        vehicle.setName("ID.3");
        vehicle.setBrand("VW");
        vehicle.setKilometerCount(5000);
        vehicle.setConstructionYear(2022);
        vehicle.setType("Electric");
        vehicle.setAvailable(true);
        vehicle.setDailyPrice(BigDecimal.valueOf(79));
        vehicle.setLicensePlate("EF-CH-360");
        vehicle.setDeposit(BigDecimal.valueOf(500));
        return vehicle;
    }
}
