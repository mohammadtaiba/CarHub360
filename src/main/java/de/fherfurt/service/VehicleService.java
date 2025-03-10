package de.fherfurt.service;

import de.fherfurt.core.entity.Vehicle;
import de.fherfurt.core.repository.VehicleRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.Year;
import java.util.List;

/**
 * Manages a collection of vehicles, including operations for creating,
 * updating, deleting, and retrieving vehicle information, now with DB persistence.
 */
@Stateless
public class VehicleService {

    @Inject
    private VehicleRepository vehicleRepository;

    /**
     * Creates a new vehicle and saves it to the database.
     */
    public boolean createVehicle(int vehicleId,
                                 String name,
                                 String brand,
                                 int kilometerCount,
                                 int constructionYear,
                                 String type) {

        if (vehicleId < 0 || name == null || brand == null || kilometerCount < 0 ||
                constructionYear < 1900 || type == null) {
            return false;
        }

        // Prüfen, ob das Baujahr plausibel ist
        Year currentYear = Year.now();
        if (currentYear.getValue() < constructionYear) {
            return false;
        }

        // Prüfen, ob bereits ein Vehicle mit dieser ID existiert
        Vehicle existing = vehicleRepository.findById(vehicleId);
        if (existing != null) {
            return false;
        }

        Vehicle newVehicle = new Vehicle(vehicleId, name, brand, kilometerCount, constructionYear, type);
        vehicleRepository.save(newVehicle);
        return true;
    }

    /**
     * Updates an existing vehicle's details.
     */
    public boolean updateVehicle(int vehicleId,
                                 String name,
                                 String brand,
                                 int kilometerCount,
                                 int constructionYear,
                                 String type) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId);
        if (vehicle == null) {
            return false;
        }
        vehicle.setName(name);
        vehicle.setBrand(brand);
        vehicle.setKilometerCount(kilometerCount);
        vehicle.setConstructionYear(constructionYear);
        vehicle.setType(type);
        vehicleRepository.update(vehicle);
        return true;
    }

    /**
     * Deletes an existing vehicle by its ID.
     */
    public boolean deleteVehicle(int vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId);
        if (vehicle != null) {
            vehicleRepository.delete(vehicleId);
            return true;
        }
        return false;
    }

    /**
     * Checks and updates the kilometer count of a vehicle.
     */
    public boolean checkNewKilometerCount(int vehicleId, int newKilometerCount) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId);
        if (vehicle != null) {
            if (newKilometerCount >= vehicle.getKilometerCount()) {
                vehicle.setKilometerCount(newKilometerCount);
                vehicleRepository.update(vehicle);
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Retrieves details of a vehicle by its ID.
     */
    public String getVehicleDetails(int vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId);
        if (vehicle == null) {
            return "Vehicle with ID " + vehicleId + " was not found.";
        }
        return "Vehicle Details: \n" +
                "Vehicle ID: " + vehicle.getVehicleId() + "\n" +
                "Name: " + vehicle.getName() + "\n" +
                "Brand: " + vehicle.getBrand() + "\n" +
                "Kilometer Count: " + vehicle.getKilometerCount() + "\n" +
                "Construction Year: " + vehicle.getConstructionYear() + "\n" +
                "Type: " + vehicle.getType();
    }

    /**
     * Beispiel: Liste aller Fahrzeuge aus der Datenbank abrufen.
     */
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
