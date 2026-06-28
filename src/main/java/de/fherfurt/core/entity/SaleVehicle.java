package de.fherfurt.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_vehicles")
@PrimaryKeyJoinColumn(name = "vehicle_id")
public class SaleVehicle extends Vehicle {

    @NotNull
    @Positive
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal salePrice;

    @Column(nullable = false)
    private boolean newVehicle;

    public SaleVehicle() {
    }

    public SaleVehicle(int vehicleId,
                       String name,
                       String brand,
                       int kilometerCount,
                       int constructionYear,
                       String type,
                       BigDecimal salePrice,
                       boolean newVehicle) {
        super(vehicleId, name, brand, kilometerCount, constructionYear, type);
        this.salePrice = salePrice;
        this.newVehicle = newVehicle;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public boolean isNew() {
        return newVehicle;
    }

    public boolean isNewVehicle() {
        return newVehicle;
    }

    public void setNew(boolean newVehicle) {
        this.newVehicle = newVehicle;
    }

    public void setNewVehicle(boolean newVehicle) {
        this.newVehicle = newVehicle;
    }
}
