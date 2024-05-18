package de.fherfurt.SaleVehicle;

import java.math.BigDecimal;

public class SaleVehicle {
    private int vehicleId;
    private BigDecimal salePrice;
    private boolean isNew;

    public SaleVehicle(int vehicleId, BigDecimal salePrice, boolean isNew) {
        this.vehicleId = vehicleId;
        this.salePrice = salePrice;
        this.isNew = isNew;
    }

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

    // Add the getDetails() method
    public String getDetails() {
        return "Vehicle ID: " + vehicleId + ", Sale Price: " + salePrice + ", Is New: " + isNew;
    }
}
