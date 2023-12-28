package de.fherfurt;

import java.util.Date;

public class RentVehicle
{
    private int RentVehicleId;
    private int RentVehicleVehicleId;
    private boolean IsAvailable;
    private float DailyPrice;
    private String LicensePlate;
    private Date StartDate;
    private Date EndDate;
    private float Deposit;

    public RentVehicle(int rentVehicleId, int rentVehicleVehicleId, boolean isAvailable, float dailyPrice, String licensePlate, Date startDate, Date endDate, float deposit) {
        RentVehicleId = rentVehicleId;
        RentVehicleVehicleId = rentVehicleVehicleId;
        IsAvailable = isAvailable;
        DailyPrice = dailyPrice;
        LicensePlate = licensePlate;
        StartDate = startDate;
        EndDate = endDate;
        Deposit = deposit;
    }

    public int getRentVehicleId() {
        return RentVehicleId;
    }

    public void setRentVehicleId(int rentVehicleId) {
        RentVehicleId = rentVehicleId;
    }

    public int getRentVehicleVehicleId() {
        return RentVehicleVehicleId;
    }

    public void setRentVehicleVehicleId(int rentVehicleVehicleId) {
        RentVehicleVehicleId = rentVehicleVehicleId;
    }

    public boolean isAvailable() {
        return IsAvailable;
    }

    public void setAvailable(boolean available) {
        IsAvailable = available;
    }

    public float getDailyPrice() {
        return DailyPrice;
    }

    public void setDailyPrice(float dailyPrice) {
        DailyPrice = dailyPrice;
    }

    public String getLicensePlate() {
        return LicensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        LicensePlate = licensePlate;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public float getDeposit() {
        return Deposit;
    }

    public void setDeposit(float deposit) {
        Deposit = deposit;
    }
}
