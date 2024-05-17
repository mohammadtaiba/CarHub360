package de.fherfurt.contract;

import de.fherfurt.RentVehicle.RentVehicle;
import de.fherfurt.SaleVehicle.SaleVehicle;
import de.fherfurt.customer.Customer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Contract {
    /* Attributes */
    private List<Contract> contracts = new ArrayList<>();

    private int contractId;
    private Customer customer;
    private int customerId;
    private SaleVehicle saleVehicle;
    private RentVehicle rentVehicle;
    private boolean isRentalContract;
    private LocalDate contractDate;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;

    /* Constructor */
    public Contract(int contractId, Customer customer, int customerId, SaleVehicle saleVehicle, RentVehicle rentVehicle, boolean isRentalContract,
                    LocalDate contractDate, LocalDate rentalStartDate, LocalDate rentalEndDate) {
        this.contractId = contractId;
        this.customer = customer;
        this.customerId = customerId;
        this.saleVehicle = saleVehicle;
        this.rentVehicle = rentVehicle;
        this.isRentalContract = isRentalContract;
        this.contractDate = contractDate;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }

    /* Methods */
    public boolean createPurchaseContract(int contractId, Customer customer, int customerId, SaleVehicle saleVehicle) {
        if (contractId >= 0 && customerId >= 0 && saleVehicle != null && !contractExists(contractId)) {
            Contract contract = new Contract(contractId, customer, customerId, saleVehicle, null, false, LocalDate.now(), null, null);
            contracts.add(contract);
            return true;
        }
        return false;
    }

    public boolean createRentalContract(int contractId, Customer customer, int customerId, RentVehicle rentVehicle,
                                        LocalDate rentalStartdate, LocalDate rentalEnddate) {
        if (contractId >= 0 && customerId >= 0 && rentVehicle != null
                && validateRentalPeriod(rentalStartdate, rentalEnddate)
                && !contractExists(contractId)) {
            Contract contract = new Contract(contractId, customer, customerId, null, rentVehicle, true,
                    LocalDate.now(), rentalStartdate, rentalEnddate);
            contracts.add(contract);
            return true;
        }
        return false;
    }

    public boolean terminateRentalContract(int contractId) {
        Contract contract = getContractById(contractId);
        if (contract != null && contract.isRentalContract) {
            contract.setRentalEndDate(LocalDate.now());
            contract.getRentVehicle().setAvailable(true);
            return true;
        }
        return false;
    }

    public boolean renewRentalContract(int contractId, LocalDate newRentalEnddate) {
        Contract contract = getContractById(contractId);
        if (contract != null && contract.isRentalContract && validateRentalPeriod(contract.getRentalStartDate(), newRentalEnddate)) {
            contract.setRentalEndDate(newRentalEnddate);
            contract.getRentVehicle().setAvailable(false);
            return true;
        }
        return false;
    }

    public double getTotalPrice(int contractId) {
        Contract contract = getContractById(contractId);
        if (contract != null && contract.isRentalContract) {
            long daysRented = ChronoUnit.DAYS.between(contract.getRentalStartDate(), contract.getRentalEndDate());
            return daysRented * contract.getRentVehicle().getDailyPrice();
        }
        return -1;
    }

    public String getRentalContractDetails(int contractId) {
        Contract contract = getContractById(contractId);
        if (contract != null && contract.isRentalContract) {
            return "Rental Contract Details: \n" +
                    "Contract ID: " + contractId + "\n" +
                    "Customer: " + contract.getCustomer().getCustomerDetails(contract.getCustomerId()) + "\n" +
                    "Rent Vehicle: " + contract.getRentVehicle().getRentVehicleDetails() + "\n" +
                    "Rental Start Date: " + contract.getRentalStartDate() + "\n" +
                    "Rental End Date: " + contract.getRentalEndDate();
        }
        return "No rental contract found with this ID.";
    }

    public String getPurchaseContractDetails(int contractId) {
        Contract contract = getContractById(contractId);
        if (contract != null && !contract.isRentalContract) {
            return "Purchase Contract Details: \n" +
                    "Contract ID: " + contractId + "\n" +
                    "Customer: " + contract.getCustomer().getCustomerDetails(contract.getCustomerId()) + "\n" +
                    "Sale Vehicle: " + contract.getSaleVehicle().getSaleVehicleDetails();
        }
        return "No purchase contract found with this ID.";
    }

    public static boolean validateRentalPeriod(LocalDate startDate, LocalDate endDate) {
        return startDate != null &&
                endDate != null &&
                !startDate.isAfter(endDate) &&
                !startDate.isBefore(LocalDate.now());
    }

    /* Utility Methods */
    private boolean contractExists(int contractId) {
        return contracts.stream().anyMatch(contract -> contract.getContractId() == contractId);
    }

    private Contract getContractById(int contractId) {
        return contracts.stream().filter(contract -> contract.getContractId() == contractId).findFirst().orElse(null);
    }

    /* Setter & Getter Methods */
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
