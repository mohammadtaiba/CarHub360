package de.fherfurt.carhub360.vehicle.rent.dto;

import de.fherfurt.carhub360.vehicle.dto.VehicleResponse;
import java.math.BigDecimal;

public class RentVehicleResponse extends VehicleResponse {

    private boolean available;
    private BigDecimal dailyPrice;
    private String licensePlate;
    private BigDecimal deposit;

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
