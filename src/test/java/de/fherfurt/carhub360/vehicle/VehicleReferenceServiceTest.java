package de.fherfurt.carhub360.vehicle;

import org.junit.jupiter.api.Test;

import static de.fherfurt.carhub360.testsupport.InjectionSupport.inject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VehicleReferenceServiceTest {

    private final FakeVehicleRepository repository = new FakeVehicleRepository();
    private final VehicleReferenceService service = new VehicleReferenceService();

    VehicleReferenceServiceTest() {
        inject(service, "vehicleRepository", repository);
    }

    @Test
    void requireVehicleReturnsExistingVehicle() {
        Vehicle vehicle = new Vehicle();
        repository.vehicle = vehicle;

        assertSame(vehicle, service.requireVehicle(1));
    }

    @Test
    void requireVehicleRejectsMissingVehicle() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.requireVehicle(1)
        );

        assertEquals("vehicleId does not reference an existing vehicle.", exception.getMessage());
    }

    private static class FakeVehicleRepository extends VehicleRepository {

        private Vehicle vehicle;

        @Override
        public Vehicle findById(int vehicleId) {
            return vehicle;
        }
    }
}
