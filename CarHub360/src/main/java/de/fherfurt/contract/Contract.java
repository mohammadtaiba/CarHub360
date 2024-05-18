package de.fherfurt.contract;

import de.fherfurt.customer.Customer;
import de.fherfurt.RentVehicle.RentVehicle;
import de.fherfurt.SaleVehicle.SaleVehicle;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class Contract {
    private static List<Contract> contracts = new ArrayList<>();

    private int contractId;
    private Customer customer;
    private SaleVehicle saleVehicle;
    private RentVehicle rentVehicle;
    private boolean isRentalContract;
    private LocalDate contractDate;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;

    public Contract(int contractId, Customer customer, SaleVehicle saleVehicle, RentVehicle rentVehicle,
                    boolean isRentalContract, LocalDate contractDate, LocalDate rentalStartDate, LocalDate rentalEndDate) {
        this.contractId = contractId;
        this.customer = customer;
        this.saleVehicle = saleVehicle;
        this.rentVehicle = rentVehicle;
        this.isRentalContract = isRentalContract;
        this.contractDate = contractDate;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }

    public static boolean createPurchaseContract(int contractId, Customer customer, SaleVehicle saleVehicle) {
        if (isValidPurchaseContract(contractId, customer, saleVehicle)) {
            Contract contract = new Contract(contractId, customer, saleVehicle, null, false,
                    LocalDate.now(), null, null);
            contracts.add(contract);
            return true;
        }
        return false;
    }

    public static boolean createRentalContract(int contractId, Customer customer, RentVehicle rentVehicle,
                                               LocalDate rentalStartDate, LocalDate rentalEndDate) {
        if (isValidRentalContract(contractId, customer, rentVehicle, rentalStartDate, rentalEndDate)) {
            Contract contract = new Contract(contractId, customer, null, rentVehicle, true,
                    LocalDate.now(), rentalStartDate, rentalEndDate);
            contracts.add(contract);
            return true;
        }
        return false;
    }

    public static boolean terminateRentalContract(int contractId) {
        Contract contract = getContractById(contractId);
        if (contract != null && contract.isRentalContract) {
            contract.setRentalEndDate(LocalDate.now());
            contract.getRentVehicle().setAvailable(true);
            return true;
        }
        return false;
    }

    public static boolean renewRentalContract(int contractId, LocalDate newRentalEndDate) {
        Contract contract = getContractById(contractId);
        if (contract != null && contract.isRentalContract && validateRentalPeriod(contract.getRentalStartDate(), newRentalEndDate)) {
            contract.setRentalEndDate(newRentalEndDate);
            contract.getRentVehicle().setAvailable(false);
            return true;
        }
        return false;
    }

    public static BigDecimal getTotalPrice(int contractId) {
        Contract contract = getContractById(contractId);
        if (contract != null && contract.isRentalContract) {
            long daysRented = ChronoUnit.DAYS.between(contract.getRentalStartDate(), contract.getRentalEndDate());
            return BigDecimal.valueOf(daysRented).multiply(contract.getRentVehicle().getDailyPrice());
        }
        return BigDecimal.valueOf(-1);
    }

    public static String getRentalContractDetails(int contractId) {
        Contract contract = getContractById(contractId);
        if (contract != null && contract.isRentalContract) {
            return "Rental Contract Details:\n" +
                    "Contract ID: " + contractId + "\n" +
                    "Customer: " + contract.getCustomer().getDetails() + "\n" +
                    "Rent Vehicle: " + contract.getRentVehicle().getDetails() + "\n" +
                    "Rental Start Date: " + contract.getRentalStartDate() + "\n" +
                    "Rental End Date: " + contract.getRentalEndDate();
        }
        return "No rental contract found with this ID.";
    }

    public static String getPurchaseContractDetails(int contractId) {
        Contract contract = getContractById(contractId);
        if (contract != null && !contract.isRentalContract) {
            return "Purchase Contract Details:\n" +
                    "Contract ID: " + contractId + "\n" +
                    "Customer: " + contract.getCustomer().getDetails() + "\n" +
                    "Sale Vehicle: " + contract.getSaleVehicle().getDetails();
        }
        return "No purchase contract found with this ID.";
    }

    private static boolean isValidPurchaseContract(int contractId, Customer customer, SaleVehicle saleVehicle) {
        return contractId >= 0 && customer != null && saleVehicle != null && !contractExists(contractId);
    }

    private static boolean isValidRentalContract(int contractId, Customer customer, RentVehicle rentVehicle,
                                                 LocalDate rentalStartDate, LocalDate rentalEndDate) {
        return contractId >= 0 && customer != null && rentVehicle != null &&
                validateRentalPeriod(rentalStartDate, rentalEndDate) && !contractExists(contractId);
    }

    private static boolean validateRentalPeriod(LocalDate startDate, LocalDate endDate) {
        return startDate != null && endDate != null && !startDate.isAfter(endDate) && !startDate.isBefore(LocalDate.now());
    }

    private static boolean contractExists(int contractId) {
        return contracts.stream().anyMatch(contract -> contract.getContractId() == contractId);
    }

    private static Contract getContractById(int contractId) {
        return contracts.stream().filter(contract -> contract.getContractId() == contractId).findFirst().orElse(null);
    }

    // Getter and Setter methods

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SaleVehicle getSaleVehicle() {
        return saleVehicle;
    }

    public void setSaleVehicle(SaleVehicle saleVehicle) {
        this.saleVehicle = saleVehicle;
    }

    public RentVehicle getRentVehicle() {
        return rentVehicle;
    }

    public void setRentVehicle(RentVehicle rentVehicle) {
        this.rentVehicle = rentVehicle;
    }

    public boolean isRentalContract() {
        return isRentalContract;
    }

    public void setRentalContract(boolean rentalContract) {
        isRentalContract = rentalContract;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(LocalDate rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public LocalDate getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(LocalDate rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }
}
