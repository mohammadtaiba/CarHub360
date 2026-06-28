package de.fherfurt.service;

import de.fherfurt.core.entity.SaleVehicle;
import de.fherfurt.core.repository.SaleVehicleRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Stateless
public class SaleVehicleService {

    @Inject
    private SaleVehicleRepository repository;

    public List<SaleVehicle> findAll() {
        return repository.findAll();
    }

    public SaleVehicle findById(int vehicleId) {
        return repository.findById(vehicleId);
    }

    public SaleVehicle create(SaleVehicle saleVehicle) {
        validate(saleVehicle);
        repository.save(saleVehicle);
        return saleVehicle;
    }

    public SaleVehicle update(int vehicleId, SaleVehicle updatedVehicle) {
        SaleVehicle existing = repository.findById(vehicleId);
        if (existing == null) {
            return null;
        }
        validate(updatedVehicle);
        existing.setName(updatedVehicle.getName());
        existing.setBrand(updatedVehicle.getBrand());
        existing.setKilometerCount(updatedVehicle.getKilometerCount());
        existing.setConstructionYear(updatedVehicle.getConstructionYear());
        existing.setType(updatedVehicle.getType());
        existing.setSalePrice(updatedVehicle.getSalePrice());
        existing.setNewVehicle(updatedVehicle.isNewVehicle());
        return repository.update(existing);
    }

    public boolean delete(int vehicleId) {
        if (repository.findById(vehicleId) == null) {
            return false;
        }
        repository.delete(vehicleId);
        return true;
    }

    private void validate(SaleVehicle vehicle) {
        validateVehicleFields(vehicle);
        if (vehicle.getSalePrice() == null || vehicle.getSalePrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("salePrice must be greater than zero.");
        }
    }

    private void validateVehicleFields(SaleVehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Sale vehicle payload is required.");
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
