package de.fherfurt.core.maintenance;

import de.fherfurt.model.vehicle.Vehicle;
import java.util.Date;

/**
 * Represents a single maintenance record for a vehicle.
 */
public class Maintenance {

    private int maintenanceId;
    private Vehicle vehicle;
    private Date maintenanceStartDate;
    private Date maintenanceEndDate;
    private float maintenanceCost;
    private String maintenanceDescription;

    public Maintenance(int maintenanceId, Vehicle vehicle, Date maintenanceStartDate, Date maintenanceEndDate,
                       float maintenanceCost, String maintenanceDescription) {
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

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getMaintenanceStartDate() {
        return maintenanceStartDate;
    }

    public void setMaintenanceStartDate(Date maintenanceStartDate) {
        this.maintenanceStartDate = maintenanceStartDate;
    }

    public Date getMaintenanceEndDate() {
        return maintenanceEndDate;
    }

    public void setMaintenanceEndDate(Date maintenanceEndDate) {
        this.maintenanceEndDate = maintenanceEndDate;
    }

    public float getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(float maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public String getMaintenanceDescription() {
        return maintenanceDescription;
    }

    public void setMaintenanceDescription(String maintenanceDescription) {
        this.maintenanceDescription = maintenanceDescription;
    }
}
