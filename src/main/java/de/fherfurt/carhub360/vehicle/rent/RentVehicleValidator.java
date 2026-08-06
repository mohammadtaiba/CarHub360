package de.fherfurt.carhub360.vehicle.rent;

import de.fherfurt.carhub360.shared.validation.RequiredText;
import de.fherfurt.carhub360.vehicle.VehicleFields;
import jakarta.ejb.Stateless;
import java.math.BigDecimal;

@Stateless
public class RentVehicleValidator {

    public void validate(RentVehicle vehicle) {
        VehicleFields.validate(vehicle, "Rent vehicle payload is required.");
        if (vehicle.getDailyPrice() == null || vehicle.getDailyPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("dailyPrice must be greater than zero.");
        }
        if (vehicle.getDeposit() == null || vehicle.getDeposit().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("deposit must not be negative.");
        }
        RequiredText.require(vehicle.getLicensePlate(), "licensePlate");
    }
}
