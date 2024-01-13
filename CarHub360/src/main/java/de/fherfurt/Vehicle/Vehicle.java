package de.fherfurt.Vehicle;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class Vehicle {


    private static class VehicleData {
        private int VehicleId;
        private String Name;
        private String Brand;
        private int KilometerCount;
        private int ConstructionYear;
        private String Type;

        public VehicleData(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
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

    }

    private Map<Integer, Vehicle.VehicleData> Vehicles = new HashMap<>();

    public boolean CreateVehicle(int VehicleId, String Name, String Brand, int KilometerCount,int ConstructionYear,String Type)
    {
        if (!Vehicles.containsKey(VehicleId)) {
        Year currentYear = Year.now();
        if (VehicleId >= 0 && Name != null && Brand != null && KilometerCount >= 0 && currentYear.getValue() >= ConstructionYear && ConstructionYear >= 1900 && Type != null ) {
            Vehicle.VehicleData newVehicle = new Vehicle.VehicleData(VehicleId, Name, Brand, KilometerCount, ConstructionYear, Type);

            Vehicles.put(VehicleId, newVehicle);

            return true;
        } else {
            System.out.println("Invalid vehicle details. Vehicle not created.");
            return false;
        }

        } else {
            System.out.println("VehicleId is taken");
            return false;
        }

    }
    public boolean UpdateVehicle(int VehicleId, String Name, String Brand, int KilometerCount,int ConstructionYear,String Type)
    {
        if (Vehicles.containsKey(VehicleId)) {

            Vehicle.VehicleData existingVehicle = Vehicles.get(VehicleId);

            existingVehicle.setName(Name);
            existingVehicle.setBrand(Brand);
            existingVehicle.setKilometerCount(KilometerCount);
            existingVehicle.setConstructionYear(ConstructionYear);
            existingVehicle.setType(Type);

            return true;
        }
        return false;
    }

    public boolean DeleteVehicle(int VehicleId) {

        if (Vehicles.containsKey(VehicleId)) {

            Vehicles.remove(VehicleId);
            return true;
        }
        return false;
    }
    public boolean CheckNewKilometerCount(int VehicleId, int newKilometerCount) {

        if (Vehicles.containsKey(VehicleId)) {

            Vehicle.VehicleData existingVehicle = Vehicles.get(VehicleId);


            if (newKilometerCount >= existingVehicle.getKilometerCount()) {
                existingVehicle.setKilometerCount(newKilometerCount);
                return true;
            } else {
                System.out.println("Error: The new mileage is smaller than the current mileage.");
            }
        } else {
            System.out.println("Vehicle with ID " + VehicleId + " was not found.");
        }
        return false;
    }

    public String getVehicleDetails(int vehicleId){

        if (Vehicles.containsKey(vehicleId)) {
            Vehicle.VehicleData vehicleData = Vehicles.get(vehicleId);

            return "Vehicle Details: \n" +
                    "Vehicle ID: " + vehicleId + "\n" +
                    "Name: " + vehicleData.getName() + "\n" +
                    "Brand: " + vehicleData.getBrand() + "\n" +
                    "Kilometer Count: " + vehicleData.getKilometerCount() + "\n" +
                    "Construction Year: " + vehicleData.getConstructionYear() + "\n" +
                    "Type: " + vehicleData.getType();

        }
        else {

            return "Vehicle with ID " + vehicleId + " was not found.";
        }
    }
}





