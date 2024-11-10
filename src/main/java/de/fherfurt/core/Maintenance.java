package de.fherfurt.core;

import de.fherfurt.model.Vehicle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents maintenance information for vehicles.
 * Contains details about maintenance operations including ID, vehicle, dates, cost and description.
 */
public class Maintenance {

    private int maintenanceId;
    private Vehicle vehicle;
    private Date maintenanceStartDate;
    private Date maintenanceEndDate;
    private float maintenanceCost;
    private String maintenanceDescription;
    private List<Maintenance> maintenances;

    public Maintenance() {
        this.maintenances = new ArrayList<>();
    }

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

    /**
     * Adds new maintenance information for a vehicle.
     *
     * @param maintenanceId Unique identifier for the maintenance record
     * @param vehicle Vehicle that requires maintenance
     * @param maintenanceStartDate Start date of maintenance
     * @param maintenanceEndDate End date of maintenance
     * @param maintenanceCost Cost of maintenance
     * @param maintenanceDescription Description of maintenance work
     * @return true if maintenance was successfully added, false if vehicle is null
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
     * Retrieves detailed information about a specific maintenance record.
     *
     * @param maintenanceId ID of the maintenance record to retrieve
     * @return String containing all maintenance details if found, error message if not found
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
            } else {
                return "false";

            }
        }
        return "Maintenance with ID " + maintenanceId + " was not found.";
    }
}
