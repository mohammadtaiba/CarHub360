package de.fherfurt;

public class Vehicle {

    private int VehicleId;
    private String Name;
    private String Brand;
    private int KilometerCount;
    private int ConstructionYear;
    private String Type;

    public Vehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
        VehicleId = vehicleId;
        Name = name;
        Brand = brand;
        KilometerCount = kilometerCount;
        ConstructionYear = constructionYear;
        Type = type;
    }

    public int getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(int vehicleId) {
        VehicleId = vehicleId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public int getKilometerCount() {
        return KilometerCount;
    }

    public void setKilometerCount(int kilometerCount) {
        KilometerCount = kilometerCount;
    }

    public int getConstructionYear() {
        return ConstructionYear;
    }

    public void setConstructionYear(int constructionYear) {
        ConstructionYear = constructionYear;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public void CreateVehicle(int VehicleId, String Name, String Brand, int KilometerCount,int ConstructionYear,String Type)
    {

    }

    public void CreateVehicle(int VehicleId)
    {

    }

}




