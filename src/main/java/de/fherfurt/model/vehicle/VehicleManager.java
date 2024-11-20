package de.fherfurt.model.vehicle;

import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages a collection of vehicles, including operations for creating,
 * updating, deleting, and retrieving vehicle information.
 */
public class VehicleManager {

    // List to store all vehicles
    private List<Vehicle> vehicles = new ArrayList<>();

    /**
     * Creates a new vehicle and adds it to the list.
     *
     * @param vehicleId        Unique identifier for the vehicle
     * @param name             Name of the vehicle
     * @param brand            Brand of the vehicle
     * @param kilometerCount   Kilometer count of the vehicle
     * @param constructionYear Construction year of the vehicle
     * @param type             Type of the vehicle
     * @return true if the vehicle is successfully created, false otherwise
     */
    public boolean createVehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
        if (vehicleId >= 0 && name != null && brand != null && kilometerCount >= 0 && constructionYear >= 1900 && type != null) {
            Year currentYear = Year.now();
            if (currentYear.getValue() >= constructionYear) {
                Vehicle newVehicle = new Vehicle(vehicleId, name, brand, kilometerCount, constructionYear, type);
                vehicles.add(newVehicle);
                return true;
            }
        }
        return false;
    }

    /**
     * Updates an existing vehicle's details.
     *
     * @param vehicleId        Unique identifier for the vehicle
     * @param name             New name of the vehicle
     * @param brand            New brand of the vehicle
     * @param kilometerCount   New kilometer count of the vehicle
     * @param constructionYear New construction year of the vehicle
     * @param type             New type of the vehicle
     * @return true if the vehicle is successfully updated, false otherwise
     */
    public boolean updateVehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId() == vehicleId) {
                vehicle.setName(name);
                vehicle.setBrand(brand);
                vehicle.setKilometerCount(kilometerCount);
                vehicle.setConstructionYear(constructionYear);
                vehicle.setType(type);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes an existing vehicle by its ID.
     *
     * @param vehicleId Unique identifier for the vehicle
     * @return true if the vehicle is successfully deleted, false otherwise
     */
    public boolean deleteVehicle(int vehicleId) {
        Iterator<Vehicle> iterator = vehicles.iterator();
        while (iterator.hasNext()) {
            Vehicle vehicle = iterator.next();
            if (vehicle.getVehicleId() == vehicleId) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Checks and updates the kilometer count of a vehicle.
     *
     * @param vehicleId         Unique identifier for the vehicle
     * @param newKilometerCount New kilometer count of the vehicle
     * @return true if the kilometer count is successfully updated, false otherwise
     */
    public boolean checkNewKilometerCount(int vehicleId, int newKilometerCount) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId() == vehicleId) {
                if (newKilometerCount >= vehicle.getKilometerCount()) {
                    vehicle.setKilometerCount(newKilometerCount);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Retrieves details of a vehicle by its ID.
     *
     * @param vehicleId Unique identifier for the vehicle
     * @return Details of the vehicle as a string, or an error message if not found
     */
    public String getVehicleDetails(int vehicleId) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId() == vehicleId) {
                return "Vehicle Details: \n" +
                        "Vehicle ID: " + vehicleId + "\n" +
                        "Name: " + vehicle.getName() + "\n" +
                        "Brand: " + vehicle.getBrand() + "\n" +
                        "Kilometer Count: " + vehicle.getKilometerCount() + "\n" +
                        "Construction Year: " + vehicle.getConstructionYear() + "\n" +
                        "Type: " + vehicle.getType();
            }
        }
        return "Vehicle with ID " + vehicleId + " was not found.";
    }
}
