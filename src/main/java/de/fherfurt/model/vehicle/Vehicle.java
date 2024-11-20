package de.fherfurt.model.vehicle;

/**
 * Represents a single vehicle with attributes such as ID, name, brand,
 * kilometer count, construction year, and type.
 */
public class Vehicle {

    // Attributes of the vehicle
    private int vehicleId;
    private String name;
    private String brand;
    private int kilometerCount;
    private int constructionYear;
    private String type;

    /**
     * Constructor to initialize the vehicle attributes.
     *
     * @param vehicleId        Unique identifier for the vehicle
     * @param name             Name of the vehicle
     * @param brand            Brand of the vehicle
     * @param kilometerCount   Kilometer count of the vehicle
     * @param constructionYear Construction year of the vehicle
     * @param type             Type of the vehicle
     */
    public Vehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
        this.vehicleId = vehicleId;
        this.name = name;
        this.brand = brand;
        this.kilometerCount = kilometerCount;
        this.constructionYear = constructionYear;
        this.type = type;
    }

    // Getter and setter methods
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
}
