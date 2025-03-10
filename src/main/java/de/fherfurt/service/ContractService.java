package de.fherfurt.core.service;

import de.fherfurt.core.entity.Contract;
import de.fherfurt.core.entity.Customer;
import de.fherfurt.core.entity.RentVehicle;
import de.fherfurt.core.entity.SaleVehicle;
import de.fherfurt.core.repository.ContractRepository;
import de.fherfurt.core.validation.ContractValidator;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * This class manages the creation, termination, and renewal of contracts,
 * using the ContractRepository for data persistence.
 */
@Stateless
public class ContractService {

    @Inject
    private ContractRepository contractRepository;

    @Inject
    private ContractValidator contractValidator;

    /**
     * Creates a purchase contract.
     */
    public boolean createPurchaseContract(int contractId, Customer customer, SaleVehicle saleVehicle) {
        if (contractValidator.isValidPurchaseContract(contractId, customer, saleVehicle)) {
            Contract contract = new Contract(
                    contractId,
                    customer,
                    saleVehicle,
                    null,
                    false,
                    LocalDate.now(),
                    null,
                    null
            );
            contractRepository.save(contract);
            return true;
        }
        return false;
    }

    /**
     * Creates a rental contract.
     */
    public boolean createRentalContract(int contractId, Customer customer, RentVehicle rentVehicle,
                                        LocalDate rentalStartDate, LocalDate rentalEndDate) {
        if (contractValidator.isValidRentalContract(contractId, customer, rentVehicle, rentalStartDate, rentalEndDate)) {
            Contract contract = new Contract(
                    contractId,
                    customer,
                    null,
                    rentVehicle,
                    true,
                    LocalDate.now(),
                    rentalStartDate,
                    rentalEndDate
            );
            contractRepository.save(contract);
            return true;
        }
        return false;
    }

    /**
     * Terminates a rental contract.
     */
    public boolean terminateRentalContract(int contractId) {
        Contract contract = contractRepository.findById(contractId);
        if (contract != null && contract.isRentalContract()) {
            contract.setRentalEndDate(LocalDate.now());
            if (contract.getRentVehicle() != null) {
                contract.getRentVehicle().setAvailable(true);
            }
            contractRepository.update(contract);
            return true;
        }
        return false;
    }

    /**
     * Renews a rental contract.
     */
    public boolean renewRentalContract(int contractId, LocalDate newRentalEndDate) {
        Contract contract = contractRepository.findById(contractId);
        if (contract != null
                && contract.isRentalContract()
                && validateRentalPeriod(contract.getRentalStartDate(), newRentalEndDate)) {
            contract.setRentalEndDate(newRentalEndDate);
            if (contract.getRentVehicle() != null) {
                contract.getRentVehicle().setAvailable(false);
            }
            contractRepository.update(contract);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the total price of a rental contract.
     */
    public BigDecimal getTotalPrice(int contractId) {
        Contract contract = contractRepository.findById(contractId);
        if (contract != null && contract.isRentalContract()) {
            long daysRented = ChronoUnit.DAYS.between(contract.getRentalStartDate(), contract.getRentalEndDate());
            return BigDecimal.valueOf(daysRented).multiply(contract.getRentVehicle().getDailyPrice());
        }
        return BigDecimal.valueOf(-1);
    }

    /**
     * Retrieves the details of a rental contract.
     */
    public String getRentalContractDetails(int contractId) {
        Contract contract = contractRepository.findById(contractId);
        if (contract != null && contract.isRentalContract()) {
            return "Rental Contract Details:\n" +
                    "Contract ID: " + contractId + "\n" +
                    "Customer: " + contract.getCustomer().getDetails() + "\n" +
                    "Rent Vehicle: " + contract.getRentVehicle().getDetails() + "\n" +
                    "Rental Start Date: " + contract.getRentalStartDate() + "\n" +
                    "Rental End Date: " + contract.getRentalEndDate();
        }
        return "No rental contract found with this ID.";
    }

    /**
     * Retrieves the details of a purchase contract.
     */
    public String getPurchaseContractDetails(int contractId) {
        Contract contract = contractRepository.findById(contractId);
        if (contract != null && !contract.isRentalContract()) {
            return "Purchase Contract Details:\n" +
                    "Contract ID: " + contractId + "\n" +
                    "Customer: " + contract.getCustomer().getDetails() + "\n" +
                    "Sale Vehicle: " + contract.getSaleVehicle().getDetails();
        }
        return "No purchase contract found with this ID.";
    }

    /**
     * Internes Hilfsverfahren zum Validieren der Mietperiode (kannst du auch in ContractValidator schieben).
     */
    private boolean validateRentalPeriod(LocalDate startDate, LocalDate endDate) {
        return startDate != null
                && endDate != null
                && !startDate.isAfter(endDate)
                && !startDate.isBefore(LocalDate.now());
    }
}
