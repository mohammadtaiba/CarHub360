package de.fherfurt.RentVehicle;

import java.math.BigDecimal;

public class RentVehicle {
    private int rentVehicleId;
    private boolean isAvailable;
    private BigDecimal dailyPrice;
    private String licensePlate;
    private BigDecimal deposit;

    // Parameterized constructor
    public RentVehicle(int rentVehicleId, boolean isAvailable, BigDecimal dailyPrice, String licensePlate, BigDecimal deposit) {
        this.rentVehicleId = rentVehicleId;
        this.isAvailable = isAvailable;
        this.dailyPrice = dailyPrice;
        this.licensePlate = licensePlate;
        this.deposit = deposit;
    }

    // Getter and setter methods
    public int getRentVehicleId() {
        return rentVehicleId;
    }

    public void setRentVehicleId(int rentVehicleId) {
        this.rentVehicleId = rentVehicleId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
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

    // Method to get the details of the RentVehicle
    public String getDetails() {
        return "Rent Vehicle ID: " + rentVehicleId +
                ", Daily Price: " + dailyPrice +
                ", License Plate: " + licensePlate;
    }
}
