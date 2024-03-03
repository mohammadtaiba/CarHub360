package de.fherfurt.RentVehicle;


import de.fherfurt.Vehicle.Vehicle;


public class RentVehicle extends Vehicle
{
    // Attributes of the RentVehicle
    private int RentVehicleId;
    private int RentVehicleVehicleId;
    private boolean IsAvailable;
    private double DailyPrice;
    private String LicensePlate;
    private float Deposit;


    // Constructor to initialize the attributes
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

    // Getter and setter methods for vehicle attributes
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

    // Method to get details of a RentVehicle
    /**
     * Retrieves details of a rented vehicle.
     * @return A string containing the details of the rented vehicle if available, or relevant information indicating unavailability.
     */
    public String getRentVehicleDetails() {
        return
                getVehicleDetails(getRentVehicleId())+ "\n" +
                "Rent Vehicle Details: \n" +
                "Rent Vehicle ID: " + getRentVehicleId() + "\n" +
                "Is Available: " + isAvailable() + "\n" +
                "Daily Price: " + getDailyPrice() + "\n" +
                "License Plate: " + getLicensePlate() + "\n" +
                "Deposit: " + getDeposit();
    }

}
