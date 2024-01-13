package de.fherfurt.RentVehicle;


import de.fherfurt.Vehicle.Vehicle;

public class RentVehicle extends Vehicle
{
    private int RentVehicleId;
    private int RentVehicleVehicleId;
    private boolean IsAvailable;
    private double DailyPrice;
    private String LicensePlate;
    private float Deposit;


    public RentVehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type,
                       int rentVehicleId, int rentVehicleVehicleId, boolean isAvailable, double dailyPrice, String licensePlate, float deposit)
    {
        super(vehicleId, name, brand, kilometerCount, constructionYear, type);
        this.RentVehicleId = rentVehicleId;
        this.RentVehicleVehicleId = rentVehicleVehicleId;
        this.IsAvailable = isAvailable;
        this.DailyPrice = dailyPrice;
        this.LicensePlate = licensePlate;
        this.Deposit = deposit;
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

    public double getDailyPrice() {
        return DailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
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

    public String getRentVehicleDetails()
    {
        return null;
    } // ergänze! <---

}
