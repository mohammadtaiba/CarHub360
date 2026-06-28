package de.fherfurt.core.validation;

import de.fherfurt.core.entity.Customer;
import de.fherfurt.core.entity.RentVehicle;
import de.fherfurt.core.entity.SaleVehicle;
import jakarta.ejb.Stateless;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ContractValidator {

    public List<String> validate(Customer customer,
                                 SaleVehicle saleVehicle,
                                 RentVehicle rentVehicle,
                                 boolean rentalContract,
                                 LocalDate rentalStartDate,
                                 LocalDate rentalEndDate) {
        List<String> errors = new ArrayList<>();

        if (customer == null || customer.isDeleted()) {
            errors.add("customerId must reference an active customer.");
        }

        if (rentalContract) {
            if (rentVehicle == null) {
                errors.add("rentVehicleId is required for rental contracts.");
            }
            if (saleVehicle != null) {
                errors.add("saleVehicleId must be empty for rental contracts.");
            }
            if (rentalStartDate == null) {
                errors.add("rentalStartDate is required for rental contracts.");
            }
            if (rentalEndDate == null) {
                errors.add("rentalEndDate is required for rental contracts.");
            }
            if (rentalStartDate != null && rentalEndDate != null && rentalEndDate.isBefore(rentalStartDate)) {
                errors.add("rentalEndDate must not be before rentalStartDate.");
            }
        } else {
            if (saleVehicle == null) {
                errors.add("saleVehicleId is required for sale contracts.");
            }
            if (rentVehicle != null) {
                errors.add("rentVehicleId must be empty for sale contracts.");
            }
        }

        return errors;
    }
}
