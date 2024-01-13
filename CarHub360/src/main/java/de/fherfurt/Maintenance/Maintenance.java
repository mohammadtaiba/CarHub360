package de.fherfurt.Maintenance;

import de.fherfurt.Vehicle.Vehicle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Maintenance {
    private static class MaintenanceInfo {
        private int MaintenanceId;
        private Vehicle Vehicle;
        private Date MaintenanceStartDate;
        private Date MaintenanceEndDate;
        private float MaintenanceCost;
        private String MaintenanceDescription;

        public MaintenanceInfo(int maintenanceId, de.fherfurt.Vehicle.Vehicle vehicle, Date maintenanceStartDate, Date maintenanceEndDate, float maintenanceCost, String maintenanceDescription) {
            MaintenanceId = maintenanceId;
            Vehicle = vehicle;
            MaintenanceStartDate = maintenanceStartDate;
            MaintenanceEndDate = maintenanceEndDate;
            MaintenanceCost = maintenanceCost;
            MaintenanceDescription = maintenanceDescription;
        }

        public int getMaintenanceId() {
            return MaintenanceId;
        }

        public void setMaintenanceId(int maintenanceId) {
            MaintenanceId = maintenanceId;
        }

        public Vehicle getVehicle() { return Vehicle; }

        public void setVehicle(Vehicle vehicle) { Vehicle = vehicle; }

        public Date getMaintenanceStartDate() {
            return MaintenanceStartDate;
        }

        public void setMaintenanceStartDate(Date maintenanceStartDate) {
            MaintenanceStartDate = maintenanceStartDate;
        }

        public Date getMaintenanceEndDate() {
            return MaintenanceEndDate;
        }

        public void setMaintenanceEndDate(Date maintenanceEndDate) {
            MaintenanceEndDate = maintenanceEndDate;
        }

        public float getMaintenanceCost() {
            return MaintenanceCost;
        }

        public void setMaintenanceCost(float maintenanceCost) {
            MaintenanceCost = maintenanceCost;
        }

        public String getMaintenanceDescription() {
            return MaintenanceDescription;
        }

        public void setMaintenanceDescription(String maintenanceDescription) {
            MaintenanceDescription = maintenanceDescription;
        }
    }

    private Map<Integer, Maintenance.MaintenanceInfo> Maintenances = new HashMap<>();

    public boolean addMaintenance(int maintenanceId, Vehicle vehicle, Date maintenanceStartDate, Date maintenanceEndDate, float maintenanceCost, String maintenanceDescription) {

        if (vehicle != null) {
            MaintenanceInfo maintenanceInfo = new MaintenanceInfo(maintenanceId, vehicle, maintenanceStartDate, maintenanceEndDate, maintenanceCost, maintenanceDescription);

            Maintenances.put(maintenanceId, maintenanceInfo);
            return true;
        }
        else {
            return false;
        }
    }

    public String getMaintenanceDetails(int maintenanceId){

        if (Maintenances.containsKey(maintenanceId)) {
            MaintenanceInfo maintenanceInfo = Maintenances.get(maintenanceId);


            return "Maintenance Details: \n" +
                    "Maintenance ID: " + maintenanceInfo.getMaintenanceId() + " \n" +
                    "Vehicle: " + maintenanceInfo.getVehicle() + " \n" +
                    "Maintenance Start Date: " + maintenanceInfo.getMaintenanceStartDate() + " \n" +
                    "Maintenance End Date: " + maintenanceInfo.getMaintenanceEndDate() + " \n" +
                    "Maintenance Cost: " + maintenanceInfo.getMaintenanceCost() + " \n" +
                    "Maintenance Description: " + maintenanceInfo.getMaintenanceDescription();
        }
        else
        {
            return "Maintenance with ID " + maintenanceId + " was not found.";
        }
    }



}
