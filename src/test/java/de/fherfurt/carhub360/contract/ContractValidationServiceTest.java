package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicle;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static de.fherfurt.carhub360.testsupport.InjectionSupport.inject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContractValidationServiceTest {

    private final ContractValidationService service = new ContractValidationService();

    ContractValidationServiceTest() {
        inject(service, "contractValidator", new ContractValidator());
    }

    @Test
    void validateAcceptsValidSaleContract() {
        ContractReferences references = new ContractReferences(activeCustomer(), new SaleVehicle(), null);

        assertDoesNotThrow(() -> service.validate(references, false, null, null));
    }

    @Test
    void validateThrowsJoinedValidationErrors() {
        ContractReferences references = new ContractReferences(activeCustomer(), new SaleVehicle(), null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.validate(references, true, null, null)
        );

        assertEquals(
                "rentVehicleId is required for rental contracts. "
                        + "saleVehicleId must be empty for rental contracts. "
                        + "rentalStartDate is required for rental contracts. "
                        + "rentalEndDate is required for rental contracts.",
                exception.getMessage()
        );
    }

    @Test
    void validateAcceptsValidRentalContract() {
        LocalDate rentalStartDate = LocalDate.now();
        ContractReferences references = new ContractReferences(activeCustomer(), null, new RentVehicle());

        assertDoesNotThrow(() -> service.validate(
                references,
                true,
                rentalStartDate,
                rentalStartDate.plusDays(3)
        ));
    }

    private Customer activeCustomer() {
        Customer customer = new Customer();
        customer.setDeleted(false);
        return customer;
    }
}
