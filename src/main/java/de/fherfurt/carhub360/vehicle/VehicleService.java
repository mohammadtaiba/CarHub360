package de.fherfurt.carhub360.vehicle;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class VehicleService {

    @Inject
    private VehicleRepository vehicleRepository;

    @Inject
    private VehicleValidator vehicleValidator;

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle findById(int vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }

    public Vehicle create(Vehicle vehicle) {
        vehicleValidator.validate(vehicle);
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public Vehicle update(int vehicleId, Vehicle updatedVehicle) {
        Vehicle existing = vehicleRepository.findById(vehicleId);
        if (existing == null) {
            return null;
        }
        vehicleValidator.validate(updatedVehicle);
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
