package de.fherfurt.service;

import de.fherfurt.core.entity.Maintenance;
import de.fherfurt.core.entity.Vehicle;
import de.fherfurt.core.repository.MaintenanceRepository;
import de.fherfurt.core.repository.VehicleRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Stateless
public class MaintenanceService {

    @Inject
    private MaintenanceRepository maintenanceRepository;

    @Inject
    private VehicleRepository vehicleRepository;

    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    public List<Maintenance> findByVehicleId(int vehicleId) {
        return maintenanceRepository.findByVehicleId(vehicleId);
    }

    public Maintenance findById(int maintenanceId) {
        return maintenanceRepository.findById(maintenanceId);
    }

    public Maintenance create(int vehicleId,
                              Date startDate,
                              Date endDate,
                              BigDecimal cost,
                              String description) {
        Vehicle vehicle = requireVehicle(vehicleId);
        validate(startDate, endDate, cost, description);
        Maintenance maintenance = new Maintenance(0, vehicle, startDate, endDate, cost, description);
        maintenanceRepository.save(maintenance);
        return maintenance;
    }

    public Maintenance update(int maintenanceId,
                              int vehicleId,
                              Date startDate,
                              Date endDate,
                              BigDecimal cost,
                              String description) {
        Maintenance existing = maintenanceRepository.findById(maintenanceId);
        if (existing == null) {
            return null;
        }
        Vehicle vehicle = requireVehicle(vehicleId);
        validate(startDate, endDate, cost, description);
        existing.setVehicle(vehicle);
        existing.setMaintenanceStartDate(startDate);
        existing.setMaintenanceEndDate(endDate);
        existing.setMaintenanceCost(cost);
        existing.setMaintenanceDescription(description);
        return maintenanceRepository.update(existing);
    }

    public boolean delete(int maintenanceId) {
        if (maintenanceRepository.findById(maintenanceId) == null) {
            return false;
        }
        maintenanceRepository.delete(maintenanceId);
        return true;
    }

    private Vehicle requireVehicle(int vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId);
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicleId does not reference an existing vehicle.");
        }
        return vehicle;
    }

    private void validate(Date startDate, Date endDate, BigDecimal cost, String description) {
        if (startDate == null) {
            throw new IllegalArgumentException("maintenanceStartDate is required.");
        }
        if (endDate != null && endDate.before(startDate)) {
            throw new IllegalArgumentException("maintenanceEndDate must not be before maintenanceStartDate.");
        }
        if (cost == null || cost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("maintenanceCost must not be negative.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("maintenanceDescription is required.");
        }
    }
}
