package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import de.fherfurt.carhub360.vehicle.rent.RentVehicleRepository;
import org.junit.jupiter.api.Test;

import static de.fherfurt.carhub360.testsupport.InjectionSupport.inject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContractRentalVehicleServiceTest {

    private final FakeRentVehicleRepository repository = new FakeRentVehicleRepository();
    private final ContractRentalVehicleService service = new ContractRentalVehicleService();

    ContractRentalVehicleServiceTest() {
        inject(service, "rentVehicleRepository", repository);
    }

    @Test
    void requireAvailableForCreateAcceptsAvailableRentalVehicle() {
        assertDoesNotThrow(() -> service.requireAvailableForCreate(true, rentVehicle(1, true)));
    }

    @Test
    void requireAvailableForCreateRejectsUnavailableRentalVehicle() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.requireAvailableForCreate(true, rentVehicle(1, false))
        );

        assertEquals("rentVehicleId references a vehicle that is not available.", exception.getMessage());
    }

    @Test
    void requireAvailableForUpdateAcceptsSameUnavailableRentalVehicle() {
        RentVehicle rentVehicle = rentVehicle(1, false);
        Contract existing = rentalContract(rentVehicle);

        assertDoesNotThrow(() -> service.requireAvailableForUpdate(existing, true, rentVehicle));
    }

    @Test
    void requireAvailableForUpdateRejectsDifferentUnavailableRentalVehicle() {
        Contract existing = rentalContract(rentVehicle(1, false));
        RentVehicle newRentVehicle = rentVehicle(2, false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.requireAvailableForUpdate(existing, true, newRentVehicle)
        );

        assertEquals("rentVehicleId references a vehicle that is not available.", exception.getMessage());
    }

    @Test
    void releaseReplacedVehicleMarksOldRentalVehicleAvailable() {
        RentVehicle oldVehicle = rentVehicle(1, false);
        RentVehicle newVehicle = rentVehicle(2, true);

        service.releaseReplacedVehicle(rentalContract(oldVehicle), newVehicle);

        assertTrue(oldVehicle.isAvailable());
        assertSame(oldVehicle, repository.updatedVehicle);
        assertEquals(1, repository.updateCount);
    }

    @Test
    void releaseReplacedVehicleKeepsSameRentalVehicleReserved() {
        RentVehicle rentVehicle = rentVehicle(1, false);

        service.releaseReplacedVehicle(rentalContract(rentVehicle), rentVehicle);

        assertFalse(rentVehicle.isAvailable());
        assertEquals(0, repository.updateCount);
    }

    @Test
    void releaseCurrentVehicleMarksRentalVehicleAvailable() {
        RentVehicle rentVehicle = rentVehicle(1, false);

        service.releaseCurrentVehicle(rentalContract(rentVehicle));

        assertTrue(rentVehicle.isAvailable());
        assertSame(rentVehicle, repository.updatedVehicle);
    }

    @Test
    void reserveIfRentalMarksRentalVehicleUnavailable() {
        RentVehicle rentVehicle = rentVehicle(1, true);

        service.reserveIfRental(true, rentVehicle);

        assertFalse(rentVehicle.isAvailable());
        assertSame(rentVehicle, repository.updatedVehicle);
    }

    private Contract rentalContract(RentVehicle rentVehicle) {
        Contract contract = new Contract();
        contract.setRentalContract(true);
        contract.setRentVehicle(rentVehicle);
        return contract;
    }

    private RentVehicle rentVehicle(int vehicleId, boolean available) {
        RentVehicle rentVehicle = new RentVehicle();
        rentVehicle.setVehicleId(vehicleId);
        rentVehicle.setAvailable(available);
        return rentVehicle;
    }

    private static class FakeRentVehicleRepository extends RentVehicleRepository {

        private RentVehicle updatedVehicle;
        private int updateCount;

        @Override
        public RentVehicle update(RentVehicle rentVehicle) {
            updatedVehicle = rentVehicle;
            updateCount++;
            return rentVehicle;
        }
    }
}
