package de.fherfurt.service;

import de.fherfurt.core.entity.Maintenance;
import de.fherfurt.core.entity.Vehicle;
import de.fherfurt.core.repository.MaintenanceRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.Date;

/**
 * Manages maintenance records for vehicles with persistent storage.
 */
@Stateless
public class MaintenanceService {

    @Inject
    private MaintenanceRepository maintenanceRepository;

    /**
     * Adds a new maintenance record to the DB.
     *
     * @param maintenanceId         Unique identifier for the maintenance record
     * @param vehicle               The vehicle associated with the maintenance
     * @param maintenanceStartDate  The start date of the maintenance
     * @param maintenanceEndDate    The end date of the maintenance
     * @param maintenanceCost       The cost of the maintenance
     * @param maintenanceDescription A description of the maintenance performed
     * @return true if the maintenance was successfully added, false if the vehicle is null
     */
    public boolean addMaintenance(int maintenanceId,
                                  Vehicle vehicle,
                                  Date maintenanceStartDate,
                                  Date maintenanceEndDate,
                                  float maintenanceCost,
                                  String maintenanceDescription) {
        if (vehicle == null) {
            return false;
        }
        // Optional: Prüfen, ob Maintenance-ID bereits existiert
        Maintenance existing = maintenanceRepository.findById(maintenanceId);
        if (existing != null) {
            // Könnte man als Update interpretieren oder als Fehler
            return false;
        }

        Maintenance maintenance = new Maintenance(
                maintenanceId,
                vehicle,
                maintenanceStartDate,
                maintenanceEndDate,
                maintenanceCost,
                maintenanceDescription
        );
        maintenanceRepository.save(maintenance);
        return true;
    }

    /**
     * Retrieves the details of a maintenance record by its ID.
     *
     * @param maintenanceId The unique identifier of the maintenance record
     * @return A string containing the details of the maintenance record, or a message indicating it was not found
     */
    public String getMaintenanceDetails(int maintenanceId) {
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId);
        if (maintenance != null) {
            // Hier z.B. .toString() auf dem Vehicle oder Vehicle-Details
            return "Maintenance Details: \n" +
                    "Maintenance ID: " + maintenance.getMaintenanceId() + " \n" +
                    "Vehicle: " + (maintenance.getVehicle() != null
                    ? maintenance.getVehicle().toString()
                    : "No vehicle assigned") + " \n" +
                    "Maintenance Start Date: " + maintenance.getMaintenanceStartDate() + " \n" +
                    "Maintenance End Date: " + maintenance.getMaintenanceEndDate() + " \n" +
                    "Maintenance Cost: " + maintenance.getMaintenanceCost() + " \n" +
                    "Maintenance Description: " + maintenance.getMaintenanceDescription();
        }
        return "Maintenance with ID " + maintenanceId + " was not found.";
    }
}
