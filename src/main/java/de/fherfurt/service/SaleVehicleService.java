package de.fherfurt.service;

import de.fherfurt.core.entity.SaleVehicle;
import de.fherfurt.core.repository.SaleVehicleRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.math.BigDecimal;

/**
 * Business logic for managing SaleVehicle entities, using persistent storage.
 */
@Stateless
public class SaleVehicleService {

    @Inject
    private SaleVehicleRepository repository;

    /**
     * Creates a new SaleVehicle if it doesn't already exist.
     */
    public boolean createSaleVehicle(int vehicleId,
                                     BigDecimal salePrice,
                                     boolean isNew) {
        // Optional: prüfen, ob ID schon existiert
        SaleVehicle existing = repository.findById(vehicleId);
        if (existing != null) {
            return false;  // Vehicle with same ID already exists
        }

        SaleVehicle sv = new SaleVehicle(vehicleId, salePrice, isNew);
        repository.save(sv);
        return true;
    }

    /**
     * Retrieves details of a SaleVehicle by ID.
     */
    public String getSaleVehicleDetails(int vehicleId) {
        SaleVehicle sv = repository.findById(vehicleId);
        if (sv == null) {
            return "SaleVehicle with ID " + vehicleId + " not found.";
        }
        return sv.getDetails();
    }

    /**
     * Example method to update sale price or "new" status, etc.
     */
    public boolean updateSaleVehiclePrice(int vehicleId, BigDecimal newPrice) {
        SaleVehicle sv = repository.findById(vehicleId);
        if (sv == null) {
            return false;
        }
        sv.setSalePrice(newPrice);
        repository.update(sv);
        return true;
    }
}
