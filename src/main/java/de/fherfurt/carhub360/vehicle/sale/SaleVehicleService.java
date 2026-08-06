package de.fherfurt.carhub360.vehicle.sale;

import de.fherfurt.carhub360.vehicle.VehicleFields;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class SaleVehicleService {

    @Inject
    private SaleVehicleRepository repository;

    @Inject
    private SaleVehicleValidator saleVehicleValidator;

    public List<SaleVehicle> findAll() {
        return repository.findAll();
    }

    public SaleVehicle findById(int vehicleId) {
        return repository.findById(vehicleId);
    }

    public SaleVehicle create(SaleVehicle saleVehicle) {
        saleVehicleValidator.validate(saleVehicle);
        repository.save(saleVehicle);
        return saleVehicle;
    }

    public SaleVehicle update(int vehicleId, SaleVehicle updatedVehicle) {
        SaleVehicle existing = repository.findById(vehicleId);
        if (existing == null) {
            return null;
        }
        saleVehicleValidator.validate(updatedVehicle);
        VehicleFields.copy(updatedVehicle, existing);
        existing.setSalePrice(updatedVehicle.getSalePrice());
        existing.setNewVehicle(updatedVehicle.isNewVehicle());
        return repository.update(existing);
    }

    public boolean delete(int vehicleId) {
        if (repository.findById(vehicleId) == null) {
            return false;
        }
        repository.delete(vehicleId);
        return true;
    }
}
