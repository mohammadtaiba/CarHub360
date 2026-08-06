package de.fherfurt.carhub360.vehicle;

import jakarta.ejb.Stateless;

@Stateless
public class VehicleValidator {

    public void validate(Vehicle vehicle) {
        VehicleFields.validate(vehicle, "Vehicle payload is required.");
    }
}
