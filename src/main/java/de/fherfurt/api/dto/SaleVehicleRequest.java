package de.fherfurt.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class SaleVehicleRequest extends VehicleRequest {

    @NotNull
    @Positive
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
