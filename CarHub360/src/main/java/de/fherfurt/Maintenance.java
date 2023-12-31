package de.fherfurt;

import java.util.Date;

public class Maintenance
{
    private int MaintenanceId;
    private int VehicleId;
    private Date MaintenanceStartDate;
    private Date MaintenanceEndDate;
    private float MaintenanceCost;
    private String MaintenanceDescription;

    public Maintenance(int maintenanceId, int vehicleId, Date maintenanceStartDate, Date maintenanceEndDate, float maintenanceCost, String maintenanceDescription) {
        MaintenanceId = maintenanceId;
        VehicleId = vehicleId;
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

    public int getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(int vehicleId) {
        VehicleId = vehicleId;
    }

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
