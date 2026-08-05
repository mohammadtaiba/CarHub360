package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicle;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContractValidatorTest {

    private final ContractValidator validator = new ContractValidator();

    @Test
    void validateAcceptsSaleContractShape() {
        List<String> errors = validator.validate(
                activeCustomer(),
                new SaleVehicle(),
                null,
                false,
                null,
                null
        );

        assertTrue(errors.isEmpty());
    }

    @Test
    void validateAcceptsRentalContractShape() {
        List<String> errors = validator.validate(
                activeCustomer(),
                null,
                new RentVehicle(),
                true,
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );

        assertTrue(errors.isEmpty());
    }

    @Test
    void validateRejectsMissingCustomer() {
        List<String> errors = validator.validate(null, new SaleVehicle(), null, false, null, null);

        assertEquals(List.of("customerId must reference an active customer."), errors);
    }

    @Test
    void validateRejectsDeletedCustomer() {
        Customer customer = activeCustomer();
        customer.setDeleted(true);

        List<String> errors = validator.validate(customer, new SaleVehicle(), null, false, null, null);

        assertEquals(List.of("customerId must reference an active customer."), errors);
    }

    @Test
    void validateRejectsInvalidSaleContractShape() {
        List<String> errors = validator.validate(
                activeCustomer(),
                null,
                new RentVehicle(),
                false,
                null,
                null
        );

        assertEquals(
                List.of(
                        "saleVehicleId is required for sale contracts.",
                        "rentVehicleId must be empty for sale contracts."
                ),
                errors
        );
    }

    @Test
    void validateRejectsInvalidRentalContractShape() {
        List<String> errors = validator.validate(
                activeCustomer(),
                new SaleVehicle(),
                null,
                true,
                null,
                null
        );

        assertEquals(
                List.of(
                        "rentVehicleId is required for rental contracts.",
                        "saleVehicleId must be empty for rental contracts.",
                        "rentalStartDate is required for rental contracts.",
                        "rentalEndDate is required for rental contracts."
                ),
                errors
        );
    }

    @Test
    void validateRejectsRentalEndDateBeforeStartDate() {
        LocalDate rentalStartDate = LocalDate.now();

        List<String> errors = validator.validate(
                activeCustomer(),
                null,
                new RentVehicle(),
                true,
                rentalStartDate,
                rentalStartDate.minusDays(1)
        );

        assertEquals(List.of("rentalEndDate must not be before rentalStartDate."), errors);
    }

    private Customer activeCustomer() {
        Customer customer = new Customer();
        customer.setDeleted(false);
        return customer;
    }
}
