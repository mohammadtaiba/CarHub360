package de.fherfurt.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * This class represents a vehicle available for rent.
 * It includes information such as daily price, availability, license plate, and deposit.
 */
@Entity
@Table(name = "rent_vehicles")
public class RentVehicle {

    @Id
    private int rentVehicleId;

    private boolean isAvailable;
    private BigDecimal dailyPrice;
    private String licensePlate;
    private BigDecimal deposit;

    /**
     * Parameterloser Konstruktor (für JPA erforderlich).
     */
    public RentVehicle() {
    }

    /**
     * Constructor for RentVehicle class.
     *
     * @param rentVehicleId Unique identifier for the rent vehicle
     * @param isAvailable   Indicates whether the vehicle is available for rent
     * @param dailyPrice    Daily price for renting the vehicle
     * @param licensePlate  License plate of the vehicle
     * @param deposit       Deposit required for renting the vehicle
     */
    public RentVehicle(int rentVehicleId,
                       boolean isAvailable,
                       BigDecimal dailyPrice,
                       String licensePlate,
                       BigDecimal deposit) {
        this.rentVehicleId = rentVehicleId;
        this.isAvailable = isAvailable;
        this.dailyPrice = dailyPrice;
        this.licensePlate = licensePlate;
        this.deposit = deposit;
    }

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

    /**
     * Retrieves details of the RentVehicle.
     *
     * @return Details of the RentVehicle including ID, daily price, and license plate
     */
    public String getDetails() {
        return "Rent Vehicle ID: " + rentVehicleId +
                ", Daily Price: " + dailyPrice +
                ", License Plate: " + licensePlate;
    }
}
