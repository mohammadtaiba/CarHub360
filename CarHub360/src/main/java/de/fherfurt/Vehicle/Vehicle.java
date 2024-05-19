package de.fherfurt.Vehicle;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
/**
 * This class represents a vehicle.
 * It includes attributes such as vehicle ID, name, brand, kilometer count, construction year, and type.
 */
public class Vehicle {

    // Attributes of the vehicle
    private int vehicleId;
    private String name;
    private String brand;
    private int kilometerCount;
    private int constructionYear;
    private String type;

    // List to store vehicles
    private static List<Vehicle> vehicles = new ArrayList<>();
    /**
     * Constructor to initialize the vehicle attributes.
     *
     * @param vehicleId Unique identifier for the vehicle
     * @param name Name of the vehicle
     * @param brand Brand of the vehicle
     * @param kilometerCount Kilometer count of the vehicle
     * @param constructionYear Construction year of the vehicle
     * @param type Type of the vehicle
     */
    public Vehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
        this.vehicleId = vehicleId;
        this.name = name;
        this.brand = brand;
        this.kilometerCount = kilometerCount;
        this.constructionYear = constructionYear;
        this.type = type;
    }

    // Getter and setter methods for vehicle attributes
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getKilometerCount() {
        return kilometerCount;
    }

    public void setKilometerCount(int kilometerCount) {
        this.kilometerCount = kilometerCount;
    }

    public int getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(int constructionYear) {
        this.constructionYear = constructionYear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    /**
     * Creates a new vehicle.
     *
     * @param vehicleId Unique identifier for the vehicle
     * @param name Name of the vehicle
     * @param brand Brand of the vehicle
     * @param kilometerCount Kilometer count of the vehicle
     * @param constructionYear Construction year of the vehicle
     * @param type Type of the vehicle
     * @return true if the vehicle is successfully created
     */
    public static boolean createVehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
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
     * Updates an existing vehicle.
     *
     * @param vehicleId Unique identifier for the vehicle
     * @param name Name of the vehicle
     * @param brand Brand of the vehicle
     * @param kilometerCount Kilometer count of the vehicle
     * @param constructionYear Construction year of the vehicle
     * @param type Type of the vehicle
     * @return true if the vehicle is successfully updated
     */
    public static boolean updateVehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
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
     * Deletes an existing vehicle.
     *
     * @param vehicleId Unique identifier for the vehicle
     * @return true if the vehicle is successfully deleted
     */
    public static boolean deleteVehicle(int vehicleId) {
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
     * @param vehicleId Unique identifier for the vehicle
     * @param newKilometerCount New kilometer count of the vehicle
     * @return true if the kilometer count is successfully updated
     */
    public static boolean checkNewKilometerCount(int vehicleId, int newKilometerCount) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId() == vehicleId) {
                if (newKilometerCount >= vehicle.getKilometerCount()) {
                    vehicle.setKilometerCount(newKilometerCount);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
    /**
     * Retrieves details of a vehicle.
     *
     * @param vehicleId Unique identifier for the vehicle
     * @return Details of the vehicle including ID, name, brand, kilometer count, construction year, and type
     */
    public static String getVehicleDetails(int vehicleId) {
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
