package de.fherfurt.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "rent_vehicles")
@PrimaryKeyJoinColumn(name = "vehicle_id")
public class RentVehicle extends Vehicle {

    @Column(nullable = false)
    private boolean available;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal dailyPrice;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, unique = true, length = 20)
    private String licensePlate;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal deposit;

    public RentVehicle() {
    }

    public RentVehicle(int vehicleId,
                       String name,
                       String brand,
                       int kilometerCount,
                       int constructionYear,
                       String type,
                       boolean available,
                       BigDecimal dailyPrice,
                       String licensePlate,
                       BigDecimal deposit) {
        super(vehicleId, name, brand, kilometerCount, constructionYear, type);
        this.available = available;
        this.dailyPrice = dailyPrice;
        this.licensePlate = licensePlate;
        this.deposit = deposit;
    }

    public int getRentVehicleId() {
        return getVehicleId();
    }

    public void setRentVehicleId(int rentVehicleId) {
        setVehicleId(rentVehicleId);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }
}
