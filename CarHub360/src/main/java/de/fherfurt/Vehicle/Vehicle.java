package de.fherfurt.Vehicle;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {

    // Attributes of the vehicle
    private int vehicleId;
    private String name;
    private String brand;
    private int kilometerCount;
    private int constructionYear;
    private String type;

    // List to store vehicles
    private List<Vehicle> vehicles = new ArrayList<>();

    // Constructor to initialize the vehicle attributes
    public Vehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
        this.vehicleId = vehicleId;
        this.name = name;
        this.brand = brand;
        this.kilometerCount = kilometerCount;
        this.constructionYear = constructionYear;
        this.type = type;
    }

    // Getter and setter methods for vehicle attributes

    public int getVehicleId() {return vehicleId; }

    public void setVehicleId(int vehicleId) {this.vehicleId = vehicleId; }

    public String getName() {return name; }

    public void setName(String name) {this.name = name; }

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


    // Method to create a new vehicle
    /**
     * Creates a vehicle and adds it to the vehicle list.
     * @param vehicleId Unique identifier for the vehicle.
     * @param name The name of the vehicle.
     * @param brand The brand of the vehicle.
     * @param kilometerCount The count of kilometers traveled by the vehicle.
     * @param constructionYear The year when the vehicle was constructed.
     * @param type The type or category of the vehicle.
     * @return true if the vehicle is successfully created and added to the list, false if the vehicle ID already exists or parameters are invalid.
     * */
    public boolean CreateVehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
        if (vehicleId >= 0 && name != null && brand != null && kilometerCount >= 0 && constructionYear >= 1900 && type != null) {
            Year currentYear = Year.now();
            if (currentYear.getValue() >= constructionYear) {
                Vehicle newVehicle = new Vehicle(vehicleId, name, brand, kilometerCount, constructionYear, type);
                vehicles.add(newVehicle);
                return true;
            } else {
                System.out.println("Invalid construction year. Vehicle not created.");
                return false;
            }
        } else {
            System.out.println("Invalid vehicle details. Vehicle not created.");
            return false;
        }
    }

    // Method to update an existing vehicle
    /**
     * Updates the details of a vehicle identified by the vehicleId.
     * @param vehicleId Unique identifier for the vehicle.
     * @param name The updated name of the vehicle.
     * @param brand The updated brand of the vehicle.
     * @param kilometerCount The updated count of kilometers traveled by the vehicle.
     * @param constructionYear The updated year when the vehicle was constructed.
     * @param type The updated type or category of the vehicle.
     * @return true if the vehicle details are successfully updated, false if the vehicle with the given vehicleId is not found.
     * */
    public boolean UpdateVehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
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

    // Method to delete an existing vehicle
    /**
     * Deletes a vehicle from the vehicle list identified by the vehicleId.
     * @param vehicleId Unique identifier for the vehicle to be deleted.
     * @return true if the vehicle is successfully deleted, false if the vehicle with the given vehicleId is not found.
     * */
    public boolean DeleteVehicle(int vehicleId) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId() == vehicleId) {
                vehicles.remove(vehicle);
                return true;
            }
        }
        return false;
    }

    // Method to check and update the kilometer count of a vehicle
    /**
     * Checks and updates the kilometer count for a vehicle identified by the vehicleId.
     * @param vehicleId Unique identifier for the vehicle.
     * @param newKilometerCount The new kilometer count to be set for the vehicle.
     * @return true if the new kilometer count is successfully set and is greater than or equal to the current kilometer count, false otherwise.
     */
    public boolean checkNewKilometerCount(int vehicleId, int newKilometerCount) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId() == vehicleId) {
                if (newKilometerCount >= vehicle.getKilometerCount()) {
                    vehicle.setKilometerCount(newKilometerCount);
                    return true;
                } else {
                    System.out.println("Error: The new mileage is smaller than the current mileage.");
                    return false;
                }
            }
        }
        System.out.println("Vehicle with ID " + vehicleId + " was not found.");
        return false;
    }

    // Method to get details of a vehicle
    /**
     * Retrieves details of a vehicle identified by the vehicleId.
     * @param vehicleId Unique identifier for the vehicle.
     * @return A string containing the details of the vehicle if found, or a message indicating that the vehicle with the given vehicleId was not found.
     * */
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