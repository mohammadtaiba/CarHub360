package de.fherfurt.logic.saleVehicle;

import java.math.BigDecimal;

/**
 * This class represents a vehicle available for sale.
 * It includes information such as sale price and whether it's new or used.
 */
public class SaleVehicle {
    private int vehicleId;
    private BigDecimal salePrice;
    private boolean isNew;

    /**
     * Constructor for SaleVehicle class.
     *
     * @param vehicleId Unique identifier for the vehicle
     * @param salePrice Sale price of the vehicle
     * @param isNew Indicates whether the vehicle is new
     */
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

    /**
     * Retrieves details of the SaleVehicle.
     *
     * @return Details of the SaleVehicle including ID, sale price, and whether it's new or used
     */
    public String getDetails() {
        return "Vehicle ID: " + vehicleId +
                ", Sale Price: " + salePrice +
                ", Is New: " + isNew;
    }
}
