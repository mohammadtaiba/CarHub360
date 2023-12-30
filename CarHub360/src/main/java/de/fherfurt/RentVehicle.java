package de.fherfurt;

import java.util.Date;

public class RentVehicle extends Vehicle
{
    private int RentVehicleId;
    private int RentVehicleVehicleId;
    private boolean IsAvailable;
    private float DailyPrice;
    private String LicensePlate;
    // private Date StartDate, EndDate; // --> Die brauchst du gar nicht, die habe ich bei Contract verschoben!
    private float Deposit;

    public RentVehicle(int rentVehicleId, int rentVehicleVehicleId, boolean isAvailable, float dailyPrice, String licensePlate, Date startDate, Date endDate, float deposit) {
        super(); // Ruft den Konstruktor der Basisklasse auf, ergänze!  <---
        RentVehicleId = rentVehicleId;
        RentVehicleVehicleId = rentVehicleVehicleId;
        IsAvailable = isAvailable;
        DailyPrice = dailyPrice;
        LicensePlate = licensePlate;

        Deposit = deposit;
    }

    public void GetRentVehicleDetails (int VehicleId)
    {

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

    public float getDeposit() {
        return Deposit;
    }

    public void setDeposit(float deposit) {
        Deposit = deposit;
    }
}
