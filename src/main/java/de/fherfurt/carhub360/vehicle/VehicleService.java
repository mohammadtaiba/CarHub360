package de.fherfurt.carhub360.vehicle;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class VehicleService {

    @Inject
    private VehicleRepository vehicleRepository;

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle findById(int vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }

    public Vehicle create(Vehicle vehicle) {
        VehicleFields.validate(vehicle, "Vehicle payload is required.");
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public Vehicle update(int vehicleId, Vehicle updatedVehicle) {
        Vehicle existing = vehicleRepository.findById(vehicleId);
        if (existing == null) {
            return null;
        }
        VehicleFields.validate(updatedVehicle, "Vehicle payload is required.");
        VehicleFields.copy(updatedVehicle, existing);
        return vehicleRepository.update(existing);
    }

    public boolean delete(int vehicleId) {
        if (vehicleRepository.findById(vehicleId) == null) {
            return false;
        }
        vehicleRepository.delete(vehicleId);
        return true;
    }

}
