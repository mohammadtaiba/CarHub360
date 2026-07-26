package de.fherfurt.carhub360.vehicle.dto;

import java.math.BigDecimal;

public class SaleVehicleResponse extends VehicleResponse {

    private BigDecimal salePrice;
    private boolean newVehicle;

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public boolean isNewVehicle() {
        return newVehicle;
    }

    public void setNewVehicle(boolean newVehicle) {
        this.newVehicle = newVehicle;
    }
}
