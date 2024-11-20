package de.fherfurt.core.contract;

import de.fherfurt.core.customer.Customer;
import de.fherfurt.logic.rentVehicle.RentVehicle;
import de.fherfurt.logic.saleVehicle.SaleVehicle;

import java.time.LocalDate;

/**
 * This class validates contracts.
 */
public class ContractValidator {

    public static boolean isValidPurchaseContract(int contractId, Customer customer, SaleVehicle saleVehicle) {
        return contractId >= 0 && customer != null && saleVehicle != null && !ContractManager.contractExists(contractId);
    }

    public static boolean isValidRentalContract(int contractId, Customer customer, RentVehicle rentVehicle,
                                                LocalDate rentalStartDate, LocalDate rentalEndDate) {
        return contractId >= 0 && customer != null && rentVehicle != null &&
                validateRentalPeriod(rentalStartDate, rentalEndDate) && !ContractManager.contractExists(contractId);
    }

    private static boolean validateRentalPeriod(LocalDate startDate, LocalDate endDate) {
        return startDate != null && endDate != null && !startDate.isAfter(endDate) && !startDate.isBefore(LocalDate.now());
    }
}