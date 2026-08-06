package de.fherfurt.carhub360.vehicle.sale;

import de.fherfurt.carhub360.vehicle.VehicleFields;
import jakarta.ejb.Stateless;
import java.math.BigDecimal;

@Stateless
public class SaleVehicleValidator {

    public void validate(SaleVehicle vehicle) {
        VehicleFields.validate(vehicle, "Sale vehicle payload is required.");
        if (vehicle.getSalePrice() == null || vehicle.getSalePrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("salePrice must be greater than zero.");
        }
    }
}
