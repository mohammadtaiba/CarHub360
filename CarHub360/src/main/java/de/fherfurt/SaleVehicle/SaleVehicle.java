package de.fherfurt.SaleVehicle;

import de.fherfurt.Vehicle.Vehicle;

public class SaleVehicle extends Vehicle {
    // Attributes of the SaleVehicle
    private float salePrice;
    private boolean isNew;

    // Constructor to initialize the attributes
    public SaleVehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type,
                       float salePrice, boolean isNew) {
        super(vehicleId, name, brand, kilometerCount, constructionYear, type);
        this.salePrice = salePrice;
        this.isNew = isNew;
    }

    // Getter and setter methods for SaleVehicle attributes
    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    // Method to get details of a SaleVehicle
    /**
     * Retrieves details of a vehicle that is for sale.
     * @return A string containing the details of the vehicle if available, or relevant information indicating unavailability.
     */
    public String getSaleVehicleDetails() {
        return getVehicleDetails(getVehicleId()) + "\n" +
                "Sale Price: " + salePrice + "\n" +
                "Is New: " + isNew;
    }
}
