package de.fherfurt.service;

import de.fherfurt.core.entity.RentVehicle;
import de.fherfurt.core.repository.RentVehicleRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Stateless
public class RentVehicleService {

    @Inject
    private RentVehicleRepository repository;

    public List<RentVehicle> findAll() {
        return repository.findAll();
    }

    public RentVehicle findById(int rentVehicleId) {
        return repository.findById(rentVehicleId);
    }

    public RentVehicle create(RentVehicle rentVehicle) {
        validate(rentVehicle);
        repository.save(rentVehicle);
        return rentVehicle;
    }

    public RentVehicle update(int rentVehicleId, RentVehicle updatedVehicle) {
        RentVehicle existing = repository.findById(rentVehicleId);
        if (existing == null) {
            return null;
        }
        validate(updatedVehicle);
        existing.setName(updatedVehicle.getName());
        existing.setBrand(updatedVehicle.getBrand());
        existing.setKilometerCount(updatedVehicle.getKilometerCount());
        existing.setConstructionYear(updatedVehicle.getConstructionYear());
        existing.setType(updatedVehicle.getType());
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

    private void validate(RentVehicle vehicle) {
        validateVehicleFields(vehicle);
        if (vehicle.getDailyPrice() == null || vehicle.getDailyPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("dailyPrice must be greater than zero.");
        }
        if (vehicle.getDeposit() == null || vehicle.getDeposit().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("deposit must not be negative.");
        }
        requireText(vehicle.getLicensePlate(), "licensePlate");
    }

    private void validateVehicleFields(RentVehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Rent vehicle payload is required.");
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

    private void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
    }
}
