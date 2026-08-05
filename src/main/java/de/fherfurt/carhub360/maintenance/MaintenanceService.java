package de.fherfurt.carhub360.maintenance;

import de.fherfurt.carhub360.vehicle.Vehicle;
import de.fherfurt.carhub360.vehicle.VehicleRepository;
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

    @Inject
    private MaintenanceValidator maintenanceValidator;

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
        maintenanceValidator.validate(startDate, endDate, cost, description);
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
        maintenanceValidator.validate(startDate, endDate, cost, description);
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
}
