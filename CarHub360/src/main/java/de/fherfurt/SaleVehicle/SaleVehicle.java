package de.fherfurt.SaleVehicle;

import java.math.BigDecimal;

public class SaleVehicle {
    private int vehicleId;
    private BigDecimal salePrice;
    private boolean isNew;

    // Parameterized constructor
    public SaleVehicle(int vehicleId, BigDecimal salePrice, boolean isNew) {
        this.vehicleId = vehicleId;
        this.salePrice = salePrice;
        this.isNew = isNew;
    }

    // Getter and setter methods
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    // Method to get the details of the SaleVehicle
    public String getDetails() {
        return "Vehicle ID: " + vehicleId +
                ", Sale Price: " + salePrice +
                ", Is New: " + isNew;
    }
}
