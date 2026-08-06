package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import de.fherfurt.carhub360.vehicle.rent.RentVehicleRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class ContractRentalVehicleService {

    private static final String UNAVAILABLE_RENT_VEHICLE =
            "rentVehicleId references a vehicle that is not available.";

    @Inject
    private RentVehicleRepository rentVehicleRepository;

    public void requireAvailableForCreate(boolean rentalContract, RentVehicle rentVehicle) {
        if (rentalContract && rentVehicle != null && !rentVehicle.isAvailable()) {
            throw new IllegalArgumentException(UNAVAILABLE_RENT_VEHICLE);
        }
    }

    public void requireAvailableForUpdate(Contract existing, boolean rentalContract, RentVehicle rentVehicle) {
        if (rentalContract
                && rentVehicle != null
                && !rentVehicle.isAvailable()
                && !isSameRentalVehicle(existing, rentVehicle)) {
            throw new IllegalArgumentException(UNAVAILABLE_RENT_VEHICLE);
        }
    }

    public void releaseReplacedVehicle(Contract existing, RentVehicle newRentVehicle) {
        if (!hasRentalVehicle(existing) || isSameRentalVehicle(existing, newRentVehicle)) {
            return;
        }
        updateAvailability(existing.getRentVehicle(), true);
    }

    public void releaseCurrentVehicle(Contract contract) {
        if (hasRentalVehicle(contract)) {
            updateAvailability(contract.getRentVehicle(), true);
        }
    }

    public void reserveIfRental(boolean rentalContract, RentVehicle rentVehicle) {
        if (rentalContract && rentVehicle != null) {
            updateAvailability(rentVehicle, false);
        }
    }

    private boolean hasRentalVehicle(Contract contract) {
        return contract != null && contract.isRentalContract() && contract.getRentVehicle() != null;
    }

    private boolean isSameRentalVehicle(Contract contract, RentVehicle rentVehicle) {
        return contract != null
                && contract.getRentVehicle() != null
                && rentVehicle != null
                && contract.getRentVehicle().getVehicleId() == rentVehicle.getVehicleId();
    }

    private void updateAvailability(RentVehicle rentVehicle, boolean available) {
        rentVehicle.setAvailable(available);
        rentVehicleRepository.update(rentVehicle);
    }
}
