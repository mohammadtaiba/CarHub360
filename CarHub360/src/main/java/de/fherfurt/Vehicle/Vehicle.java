package de.fherfurt.Vehicle;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {

    private static class VehicleData {
        private int vehicleId;
        private String name;
        private String brand;
        private int kilometerCount;
        private int constructionYear;
        private String type;

        public VehicleData(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
            this.vehicleId = vehicleId;
            this.name = name;
            this.brand = brand;
            this.kilometerCount = kilometerCount;
            this.constructionYear = constructionYear;
            this.type = type;
        }

        public int getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(int vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public int getKilometerCount() {
            return kilometerCount;
        }

        public void setKilometerCount(int kilometerCount) {
            this.kilometerCount = kilometerCount;
        }

        public int getConstructionYear() {
            return constructionYear;
        }

        public void setConstructionYear(int constructionYear) {
            this.constructionYear = constructionYear;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    private List<VehicleData> vehicles = new ArrayList<>();

    public boolean createVehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
        if (vehicleId >= 0 && name != null && brand != null && kilometerCount >= 0 && constructionYear >= 1900 && type != null) {
            Year currentYear = Year.now();
            if (currentYear.getValue() >= constructionYear) {
                VehicleData newVehicle = new VehicleData(vehicleId, name, brand, kilometerCount, constructionYear, type);
                vehicles.add(newVehicle);
                return true;
            } else {
                System.out.println("Invalid construction year. Vehicle not created.");
                return false;
            }
        } else {
            System.out.println("Invalid vehicle details. Vehicle not created.");
            return false;
        }
    }

    public boolean updateVehicle(int vehicleId, String name, String brand, int kilometerCount, int constructionYear, String type) {
        for (VehicleData vehicle : vehicles) {
            if (vehicle.getVehicleId() == vehicleId) {
                vehicle.setName(name);
                vehicle.setBrand(brand);
                vehicle.setKilometerCount(kilometerCount);
                vehicle.setConstructionYear(constructionYear);
                vehicle.setType(type);
                return true;
            }
        }
        return false;
    }

    public boolean deleteVehicle(int vehicleId) {
        for (VehicleData vehicle : vehicles) {
            if (vehicle.getVehicleId() == vehicleId) {
                vehicles.remove(vehicle);
                return true;
            }
        }
        return false;
    }

    public boolean checkNewKilometerCount(int vehicleId, int newKilometerCount) {
        for (VehicleData vehicle : vehicles) {
            if (vehicle.getVehicleId() == vehicleId) {
                if (newKilometerCount >= vehicle.getKilometerCount()) {
                    vehicle.setKilometerCount(newKilometerCount);
                    return true;
                } else {
                    System.out.println("Error: The new mileage is smaller than the current mileage.");
                    return false;
                }
            }
        }
        System.out.println("Vehicle with ID " + vehicleId + " was not found.");
        return false;
    }

    public String getVehicleDetails(int vehicleId) {
        for (VehicleData vehicle : vehicles) {
            if (vehicle.getVehicleId() == vehicleId) {
                return "Vehicle Details: \n" +
                        "Vehicle ID: " + vehicleId + "\n" +
                        "Name: " + vehicle.getName() + "\n" +
                        "Brand: " + vehicle.getBrand() + "\n" +
                        "Kilometer Count: " + vehicle.getKilometerCount() + "\n" +
                        "Construction Year: " + vehicle.getConstructionYear() + "\n" +
                        "Type: " + vehicle.getType();
            }
        }
        return "Vehicle with ID " + vehicleId + " was not found.";
    }
}
