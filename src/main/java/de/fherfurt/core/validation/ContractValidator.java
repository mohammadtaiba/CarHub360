package de.fherfurt.core.validation;

import de.fherfurt.core.entity.Contract;
import de.fherfurt.core.entity.Customer;
import de.fherfurt.core.entity.RentVehicle;
import de.fherfurt.core.entity.SaleVehicle;
import de.fherfurt.core.repository.ContractRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDate;

/**
 * This class validates contracts by checking data constraints and existing DB entries.
 */
@Stateless
public class ContractValidator {

    @Inject
    private ContractRepository contractRepository;

    public boolean isValidPurchaseContract(int contractId, Customer customer, SaleVehicle saleVehicle) {
        if (contractId < 0 || customer == null || saleVehicle == null) {
            return false;
        }
        // Prüfen, ob Contract bereits existiert
        return !contractRepository.exists(contractId);
    }

    public boolean isValidRentalContract(int contractId,
                                         Customer customer,
                                         RentVehicle rentVehicle,
                                         LocalDate rentalStartDate,
                                         LocalDate rentalEndDate) {
        if (contractId < 0 || customer == null || rentVehicle == null ||
                !validateRentalPeriod(rentalStartDate, rentalEndDate)) {
            return false;
        }
        // Prüfen, ob Contract bereits existiert
        return !contractRepository.exists(contractId);
    }

    private boolean validateRentalPeriod(LocalDate startDate, LocalDate endDate) {
        return startDate != null
                && endDate != null
                && !startDate.isAfter(endDate)
                && !startDate.isBefore(LocalDate.now());
    }
}
