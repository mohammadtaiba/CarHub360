package de.fherfurt.carhub360.vehicle.sale;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SaleVehicleValidatorTest {

    private final SaleVehicleValidator validator = new SaleVehicleValidator();

    @Test
    void validateAcceptsValidSaleVehicle() {
        assertDoesNotThrow(() -> validator.validate(saleVehicle()));
    }

    @Test
    void validateRejectsMissingSaleVehicle() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(null)
        );

        assertEquals("Sale vehicle payload is required.", exception.getMessage());
    }

    @Test
    void validateRejectsMissingSalePrice() {
        SaleVehicle vehicle = saleVehicle();
        vehicle.setSalePrice(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(vehicle)
        );

        assertEquals("salePrice must be greater than zero.", exception.getMessage());
    }

    @Test
    void validateRejectsNonPositiveSalePrice() {
        SaleVehicle vehicle = saleVehicle();
        vehicle.setSalePrice(BigDecimal.ZERO);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(vehicle)
        );

        assertEquals("salePrice must be greater than zero.", exception.getMessage());
    }

    private SaleVehicle saleVehicle() {
        SaleVehicle vehicle = new SaleVehicle();
        vehicle.setName("Golf");
        vehicle.setBrand("VW");
        vehicle.setKilometerCount(12000);
        vehicle.setConstructionYear(2021);
        vehicle.setType("Compact");
        vehicle.setSalePrice(BigDecimal.valueOf(18000));
        vehicle.setNewVehicle(false);
        return vehicle;
    }
}
