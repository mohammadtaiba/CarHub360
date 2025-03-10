package de.fherfurt.service;

import de.fherfurt.core.entity.RentVehicle;
import de.fherfurt.core.repository.RentVehicleRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.math.BigDecimal;

/**
 * Business logic for managing RentVehicle entities, using persistent storage.
 */
@Stateless
public class RentVehicleService {

    @Inject
    private RentVehicleRepository repository;

    /**
     * Creates a new RentVehicle record if it doesn't already exist.
     */
    public boolean createRentVehicle(int rentVehicleId,
                                     boolean isAvailable,
                                     BigDecimal dailyPrice,
                                     String licensePlate,
                                     BigDecimal deposit) {

        // Optional: prüfen, ob ID schon existiert
        RentVehicle existing = repository.findById(rentVehicleId);
        if (existing != null) {
            return false;  // Vehicle with same ID already exists
        }

        RentVehicle rv = new RentVehicle(rentVehicleId, isAvailable, dailyPrice, licensePlate, deposit);
        repository.save(rv);
        return true;
    }

    /**
     * Retrieves details of a RentVehicle by ID.
     */
    public String getRentVehicleDetails(int rentVehicleId) {
        RentVehicle rv = repository.findById(rentVehicleId);
        if (rv == null) {
            return "RentVehicle with ID " + rentVehicleId + " not found.";
        }
        return rv.getDetails();
    }

    /**
     * Example method to update availability or dailyPrice etc.
     */
    public boolean updateRentVehicleAvailability(int rentVehicleId, boolean newAvailability) {
        RentVehicle rv = repository.findById(rentVehicleId);
        if (rv == null) {
            return false;
        }
        rv.setAvailable(newAvailability);
        repository.update(rv);
        return true;
    }
}
