package de.fherfurt.carhub360.vehicle.rent;

import de.fherfurt.carhub360.vehicle.VehicleFields;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class RentVehicleService {

    @Inject
    private RentVehicleRepository repository;

    @Inject
    private RentVehicleValidator rentVehicleValidator;

    public List<RentVehicle> findAll() {
        return repository.findAll();
    }

    public RentVehicle findById(int rentVehicleId) {
        return repository.findById(rentVehicleId);
    }

    public RentVehicle create(RentVehicle rentVehicle) {
        rentVehicleValidator.validate(rentVehicle);
        repository.save(rentVehicle);
        return rentVehicle;
    }

    public RentVehicle update(int rentVehicleId, RentVehicle updatedVehicle) {
        RentVehicle existing = repository.findById(rentVehicleId);
        if (existing == null) {
            return null;
        }
        rentVehicleValidator.validate(updatedVehicle);
        VehicleFields.copy(updatedVehicle, existing);
        existing.setAvailable(updatedVehicle.isAvailable());
        existing.setDailyPrice(updatedVehicle.getDailyPrice());
        existing.setLicensePlate(updatedVehicle.getLicensePlate());
        existing.setDeposit(updatedVehicle.getDeposit());
        return repository.update(existing);
    }

    public boolean setAvailability(int rentVehicleId, boolean available) {
        RentVehicle vehicle = repository.findById(rentVehicleId);
        if (vehicle == null) {
            return false;
        }
        vehicle.setAvailable(available);
        repository.update(vehicle);
        return true;
    }

    public boolean delete(int rentVehicleId) {
        if (repository.findById(rentVehicleId) == null) {
            return false;
        }
        repository.delete(rentVehicleId);
        return true;
    }
}
