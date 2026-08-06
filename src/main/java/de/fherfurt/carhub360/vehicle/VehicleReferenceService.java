package de.fherfurt.carhub360.vehicle;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class VehicleReferenceService {

    @Inject
    private VehicleRepository vehicleRepository;

    public Vehicle requireVehicle(int vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId);
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicleId does not reference an existing vehicle.");
        }
        return vehicle;
    }
}
