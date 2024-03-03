package de.fherfurt.SaleVehicle;

import de.fherfurt.Vehicle.Vehicle;

public class SaleVehicle extends Vehicle
{
    // Attributes of the SaleVehicle
    private int VehicleId;
    private float SalePrice;
    private boolean IsNew;

    // Constructor to initialize the attributes
    public SaleVehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type,
                       float salePrice, boolean isNew) {
        super(vehicleId, name, brand, kilometerCount, constructionYear, type);
        this.VehicleId = vehicleId;
        this.SalePrice = salePrice;
        this.IsNew = isNew;
    }

    // Getter and setter methods for vehicle attributes
    public int getVehicleId() {return VehicleId; }
    public void setVehicleId(int vehicleId) {VehicleId = vehicleId; }

    public float getSalePrice() { return SalePrice; }
    public void setSalePrice(float salePrice) {SalePrice = salePrice;}

    public boolean isNew() {return IsNew;}
    public void setNew(boolean aNew) {IsNew = aNew;}

    // Method to get details of a SaleVehicle
    /**
     * Retrieves details of a vehicle that is for sale.
     * @return A string containing the details of the vehicle if available, or relevant information indicating unavailability.
     */
    public String getSaleVehicleDetails() {
        return getVehicleDetails(getVehicleId()) + "\n" +
                "Sale Vehicle Details: \n" +
                "Vehicle ID: " + getVehicleId() + "\n" +
                "Sale Price: " + getSalePrice() + "\n" +
                "Is New: " + isNew();
    }

}

