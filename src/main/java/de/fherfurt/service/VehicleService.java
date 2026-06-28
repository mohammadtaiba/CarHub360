package de.fherfurt.service;

import de.fherfurt.core.entity.Vehicle;
import de.fherfurt.core.repository.VehicleRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.Year;
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
        validateVehicle(vehicle);
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    public Vehicle update(int vehicleId, Vehicle updatedVehicle) {
        Vehicle existing = vehicleRepository.findById(vehicleId);
        if (existing == null) {
            return null;
        }
        validateVehicle(updatedVehicle);
        copyVehicleFields(updatedVehicle, existing);
        return vehicleRepository.update(existing);
    }

    public boolean delete(int vehicleId) {
        if (vehicleRepository.findById(vehicleId) == null) {
            return false;
        }
        vehicleRepository.delete(vehicleId);
        return true;
    }

    protected void validateVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle payload is required.");
        }
        requireText(vehicle.getName(), "name");
        requireText(vehicle.getBrand(), "brand");
        requireText(vehicle.getType(), "type");
        if (vehicle.getKilometerCount() < 0) {
            throw new IllegalArgumentException("kilometerCount must not be negative.");
        }
        int currentYear = Year.now().getValue();
        if (vehicle.getConstructionYear() < 1900 || vehicle.getConstructionYear() > currentYear + 1) {
            throw new IllegalArgumentException("constructionYear must be plausible.");
        }
    }

    protected void copyVehicleFields(Vehicle source, Vehicle target) {
        target.setName(source.getName());
        target.setBrand(source.getBrand());
        target.setKilometerCount(source.getKilometerCount());
        target.setConstructionYear(source.getConstructionYear());
        target.setType(source.getType());
    }

    private void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
    }
}
