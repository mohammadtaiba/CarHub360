package de.fherfurt.core.contract;

import de.fherfurt.core.customer.Customer;
import de.fherfurt.logic.rentVehicle.RentVehicle;
import de.fherfurt.logic.saleVehicle.SaleVehicle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the creation, termination, and renewal of contracts.
 */
public class ContractManager {
    private static List<Contract> contracts = new ArrayList<>();

    /**
     * Creates a purchase contract.
     *
     * @param contractId  The unique ID of the contract.
     * @param customer    The customer associated with the contract.
     * @param saleVehicle The vehicle involved in the sale contract.
     * @return True if the purchase contract is successfully created, false otherwise.
     */
    public static boolean createPurchaseContract(int contractId, Customer customer, SaleVehicle saleVehicle) {
        if (ContractValidator.isValidPurchaseContract(contractId, customer, saleVehicle)) {
            Contract contract = new Contract(contractId, customer, saleVehicle, null, false,
                    LocalDate.now(), null, null);
            contracts.add(contract);
            return true;
        }
        return false;
    }

    /**
     * Creates a rental contract.
     *
     * @param contractId       The unique ID of the contract.
     * @param customer         The customer associated with the contract.
     * @param rentVehicle      The vehicle involved in the rental contract.
     * @param rentalStartDate  The start date of the rental period.
     * @param rentalEndDate    The end date of the rental period.
     * @return True if the rental contract is successfully created, false otherwise.
     */
    public static boolean createRentalContract(int contractId, Customer customer, RentVehicle rentVehicle,
                                               LocalDate rentalStartDate, LocalDate rentalEndDate) {
        if (ContractValidator.isValidRentalContract(contractId, customer, rentVehicle, rentalStartDate, rentalEndDate)) {
            Contract contract = new Contract(contractId, customer, null, rentVehicle, true,
                    LocalDate.now(), rentalStartDate, rentalEndDate);
            contracts.add(contract);
            return true;
        }
        return false;
    }

    /**
     * Terminates a rental contract.
     *
     * @param contractId The unique ID of the contract.
     * @return True if the rental contract is successfully terminated, false otherwise.
     */
    public static boolean terminateRentalContract(int contractId) {
        Contract contract = getContractById(contractId);
        if (contract != null && contract.isRentalContract()) {
            contract.setRentalEndDate(LocalDate.now());
            contract.getRentVehicle().setAvailable(true);
            return true;
        }
        return false;
    }

    /**
     * Renews a rental contract.
     *
     * @param contractId         The unique ID of the contract.
     * @param newRentalEndDate   The new end date of the rental period.
     * @return True if the rental contract is successfully renewed, false otherwise.
     */
    public static boolean renewRentalContract(int contractId, LocalDate newRentalEndDate) {
        Contract contract = getContractById(contractId);
        if (contract != null && contract.isRentalContract() && validateRentalPeriod(contract.getRentalStartDate(), newRentalEndDate)) {
            contract.setRentalEndDate(newRentalEndDate);
            contract.getRentVehicle().setAvailable(false);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the total price of a rental contract.
     *
     * @param contractId The unique ID of the contract.
     * @return The total price of the rental contract, or -1 if the contract is not found or is not a rental contract.
     */
    public static BigDecimal getTotalPrice(int contractId) {
        Contract contract = getContractById(contractId);
        if (contract != null && contract.isRentalContract()) {
            long daysRented = ChronoUnit.DAYS.between(contract.getRentalStartDate(), contract.getRentalEndDate());
            return BigDecimal.valueOf(daysRented).multiply(contract.getRentVehicle().getDailyPrice());
        }
        return BigDecimal.valueOf(-1);
    }

    /**
     * Retrieves the details of a rental contract.
     *
     * @param contractId The unique ID of the contract.
     * @return A string containing the details of the rental contract if found, or a message indicating that the contract was not found.
     */
    public static String getRentalContractDetails(int contractId) {
        Contract contract = getContractById(contractId);
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
     *
     * @param contractId The unique ID of the contract.
     * @return A string containing the details of the purchase contract if found, or a message indicating that the contract was not found.
     */
    public static String getPurchaseContractDetails(int contractId) {
        Contract contract = getContractById(contractId);
        if (contract != null && !contract.isRentalContract()) {
            return "Purchase Contract Details:\n" +
                    "Contract ID: " + contractId + "\n" +
                    "Customer: " + contract.getCustomer().getDetails() + "\n" +
                    "Sale Vehicle: " + contract.getSaleVehicle().getDetails();
        }
        return "No purchase contract found with this ID.";
    }

    public static boolean contractExists(int contractId) {
        return contracts.stream().anyMatch(contract -> contract.getContractId() == contractId);
    }

    public static Contract getContractById(int contractId) {
        return contracts.stream().filter(contract -> contract.getContractId() == contractId).findFirst().orElse(null);
    }

    public static boolean validateRentalPeriod(LocalDate startDate, LocalDate endDate) {
        return startDate != null && endDate != null && !startDate.isAfter(endDate) && !startDate.isBefore(LocalDate.now());
    }
} 