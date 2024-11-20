package de.fherfurt.core.maintenance;

import de.fherfurt.model.vehicle.Vehicle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manages maintenance records for vehicles.
 */
public class MaintenanceManager {

    private List<Maintenance> maintenances;

    /**
     * Constructs a new MaintenanceManager with an empty list of maintenances.
     */
    public MaintenanceManager() {
        this.maintenances = new ArrayList<>();
    }

    /**
     * Adds a new maintenance record to the list.
     *
     * @param maintenanceId         Unique identifier for the maintenance record
     * @param vehicle               The vehicle associated with the maintenance
     * @param maintenanceStartDate  The start date of the maintenance
     * @param maintenanceEndDate    The end date of the maintenance
     * @param maintenanceCost       The cost of the maintenance
     * @param maintenanceDescription A description of the maintenance performed
     * @return true if the maintenance was successfully added, false if the vehicle is null
     */
    public boolean addMaintenance(int maintenanceId, Vehicle vehicle, Date maintenanceStartDate, Date maintenanceEndDate,
                                  float maintenanceCost, String maintenanceDescription) {
        if (vehicle != null) {
            Maintenance maintenance = new Maintenance(maintenanceId, vehicle, maintenanceStartDate, maintenanceEndDate,
                    maintenanceCost, maintenanceDescription);
            maintenances.add(maintenance);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves the details of a maintenance record by its ID.
     *
     * @param maintenanceId The unique identifier of the maintenance record
     * @return A string containing the details of the maintenance record, or a message indicating it was not found
     */
    public String getMaintenanceDetails(int maintenanceId) {
        for (Maintenance maintenance : maintenances) {
            if (maintenance.getMaintenanceId() == maintenanceId) {
                return "Maintenance Details: \n" +
                        "Maintenance ID: " + maintenance.getMaintenanceId() + " \n" +
                        "Vehicle: " + maintenance.getVehicle().toString() + " \n" +
                        "Maintenance Start Date: " + maintenance.getMaintenanceStartDate() + " \n" +
                        "Maintenance End Date: " + maintenance.getMaintenanceEndDate() + " \n" +
                        "Maintenance Cost: " + maintenance.getMaintenanceCost() + " \n" +
                        "Maintenance Description: " + maintenance.getMaintenanceDescription();
            }
        }
        return "Maintenance with ID " + maintenanceId + " was not found.";
    }
}