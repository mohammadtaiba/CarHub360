package de.fherfurt.Maintenance;

import de.fherfurt.Vehicle.Vehicle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents maintenance information for vehicles.
 * It includes attributes such as maintenance ID, vehicle, maintenance start date, maintenance end date, maintenance cost, and maintenance description.
 */
public class Maintenance {

    private int maintenanceId;
    private Vehicle vehicle;
    private Date maintenanceStartDate;
    private Date maintenanceEndDate;
    private float maintenanceCost;
    private String maintenanceDescription;
    private List<Maintenance> maintenances;

    // Default constructor to initialize the maintenances list
    public Maintenance() {
        this.maintenances = new ArrayList<>();
    }

    // Parameterized constructor to initialize the maintenance attributes
    public Maintenance(int maintenanceId, Vehicle vehicle, Date maintenanceStartDate, Date maintenanceEndDate,
                       float maintenanceCost, String maintenanceDescription) {
        this.maintenanceId = maintenanceId;
        this.vehicle = vehicle;
        this.maintenanceStartDate = maintenanceStartDate;
        this.maintenanceEndDate = maintenanceEndDate;
        this.maintenanceCost = maintenanceCost;
        this.maintenanceDescription = maintenanceDescription;
    }

    // Getter and setter methods
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

    // Method to add maintenance information for a vehicle
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

    // Method to retrieve details of a maintenance identified by the maintenanceId
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
