package de.fherfurt.Maintenance;

import de.fherfurt.Vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Maintenance {
    private static class MaintenanceInfo {
        private int maintenanceId;
        private Vehicle vehicle;
        private Date maintenanceStartDate;
        private Date maintenanceEndDate;
        private float maintenanceCost;
        private String maintenanceDescription;

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

    public boolean addMaintenance(int maintenanceId, Vehicle vehicle, Date maintenanceStartDate, Date maintenanceEndDate, float maintenanceCost, String maintenanceDescription) {
        if (vehicle != null) {
            MaintenanceInfo maintenanceInfo = new MaintenanceInfo(maintenanceId, vehicle, maintenanceStartDate, maintenanceEndDate, maintenanceCost, maintenanceDescription);
            maintenances.add(maintenanceInfo);
            return true;
        } else {
            return false;
        }
    }

    public String getMaintenanceDetails(int maintenanceId) {
        for (MaintenanceInfo maintenance : maintenances) {
            if (maintenance.getMaintenanceId() == maintenanceId) {
                return "Maintenance Details: \n" +
                        "Maintenance ID: " + maintenance.getMaintenanceId() + " \n" +
                        "Vehicle: " + maintenance.getVehicle() + " \n" +
                        "Maintenance Start Date: " + maintenance.getMaintenanceStartDate() + " \n" +
                        "Maintenance End Date: " + maintenance.getMaintenanceEndDate() + " \n" +
                        "Maintenance Cost: " + maintenance.getMaintenanceCost() + " \n" +
                        "Maintenance Description: " + maintenance.getMaintenanceDescription();
            }
        }
        return "Maintenance with ID " + maintenanceId + " was not found.";
    }
}
