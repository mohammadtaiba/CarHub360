package de.fherfurt.Maintenance;

import de.fherfurt.Vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Maintenance {

    // Inner class to hold payment details
    private static class MaintenanceInfo {
        private int maintenanceId;
        private Vehicle vehicle;
        private Date maintenanceStartDate;
        private Date maintenanceEndDate;
        private float maintenanceCost;
        private String maintenanceDescription;

        // Constructor to initialize the MaintenanceInfo attributes
        public MaintenanceInfo(int maintenanceId, Vehicle vehicle, Date maintenanceStartDate, Date maintenanceEndDate, float maintenanceCost, String maintenanceDescription) {
            this.maintenanceId = maintenanceId;
            this.vehicle = vehicle;
            this.maintenanceStartDate = maintenanceStartDate;
            this.maintenanceEndDate = maintenanceEndDate;
            this.maintenanceCost = maintenanceCost;
            this.maintenanceDescription = maintenanceDescription;
        }

        public int getMaintenanceId() {
            return maintenanceId;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public Date getMaintenanceStartDate() {
            return maintenanceStartDate;
        }

        public Date getMaintenanceEndDate() {
            return maintenanceEndDate;
        }

        public float getMaintenanceCost() {
            return maintenanceCost;
        }

        public String getMaintenanceDescription() {
            return maintenanceDescription;
        }
    }

    private List<MaintenanceInfo> maintenances = new ArrayList<>();

    /**
     * Adds maintenance information for a vehicle.
     * @param maintenanceId Unique identifier for the maintenance.
     * @param vehicle The vehicle for which maintenance information is being added.
     * @param maintenanceStartDate The start date of the maintenance.
     * @param maintenanceEndDate The end date of the maintenance.
     * @param maintenanceCost The cost incurred for the maintenance.
     * @param maintenanceDescription Description of the maintenance performed.
     * @return true if maintenance information is successfully added, false if the vehicle provided is null.
     */
    public boolean addMaintenance(int maintenanceId, Vehicle vehicle, Date maintenanceStartDate, Date maintenanceEndDate, float maintenanceCost, String maintenanceDescription) {
        if (vehicle != null) {
            MaintenanceInfo maintenanceInfo = new MaintenanceInfo(maintenanceId, vehicle, maintenanceStartDate, maintenanceEndDate, maintenanceCost, maintenanceDescription);
            maintenances.add(maintenanceInfo);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds maintenance information for a vehicle.
     * Retrieves details of a maintenance identified by the maintenanceId.
     * @param maintenanceId Unique identifier for the maintenance.
     * @return A string containing the details of the maintenance if found, or a message indicating that the maintenance with the given maintenanceId was not found.
     */
    public String getMaintenanceDetails(int maintenanceId) {
        for (MaintenanceInfo maintenance : maintenances) {
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