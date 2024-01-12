package de.fherfurt.contract;

import de.fherfurt.Customer;
import de.fherfurt.RentVehicle;
import de.fherfurt.SaleVehicle;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Contract
{
    /* Attributes */
    private Map<Integer, ContractDetails> contracts = new HashMap<>();

    /* class-Methods */
    /**
     * Creates a purchase contract and adds it to the contract collection.
     * @param contractId Unique identifier for the contract.
     * @param customer Customer associated with the purchase.
     * @param saleVehicle Vehicle that is being purchased.
     * @return true if the contract is successfully created and false if the contractId already exists or parameters are invalid.
     */
    public boolean createPurchaseContract(int contractId, Customer customer, SaleVehicle saleVehicle)
    {
        if (contractId >= 0 && customer != null && saleVehicle != null && !contracts.containsKey(contractId))
        {
            ContractDetails details = new ContractDetails(customer, saleVehicle, null, false,
                    LocalDate.now(), null, null);

            contracts.put(contractId, details);
            return true;
        }
        return false;
    }

    /**
     * Creates a rental contract and adds it to the contract collection.
     * @param contractId Unique identifier for the contract.
     * @param customer Customer associated with the rental.
     * @param rentVehicle Vehicle that is being rented.
     * @param rentalStartdate The start date of the rental period.
     * @param rentalEnddate The end date of the rental period.
     * @return true if the contract is successfully created and false if the contractId already exists or parameters are invalid.
     */
    public boolean createRentalContract(int contractId, Customer customer, RentVehicle rentVehicle,
                                        LocalDate rentalStartdate, LocalDate rentalEnddate)
    {
        if (contractId >= 0 && customer != null && rentVehicle != null
                && validateRentalPeriod(rentalStartdate, rentalEnddate)
                && !contracts.containsKey(contractId))
        {
            ContractDetails details = new ContractDetails(customer, null, rentVehicle, true,
                    LocalDate.now(), rentalStartdate, rentalEnddate);
            contracts.put(contractId, details);
            return true;
        }
        return false;
    }

    /**
     * Terminates an existing rental contract by setting its end date to the current date and making the vehicle available again.
     * @param contractId Identifier of the rental contract to be terminated.
     * @return true if the contract is successfully terminated, false if the contract is not found or is not a rental contract.
     */
    public boolean terminateRentalContract(int contractId)
    {
        ContractDetails details = contracts.get(contractId);
        if (details != null && details.isRentalContract())
        {
            details.setRentalEndDate(LocalDate.now());
            details.rentVehicle.setAvailable(true);
            return true;
        }
        System.out.println("Invalid contract details!");
        return false;
    }

    /**
     * Renews an existing rental contract with a new end date and ensures the vehicle remains unavailable.
     * @param contractId Identifier of the rental contract to be renewed.
     * @param newRentalEnddate New end date for the rental contract.
     * @return true if the contract is successfully renewed, false if the contract is not found, is not a rental contract, or the new end date is invalid.
     */
    public boolean renewRentalContract(int contractId, LocalDate newRentalEnddate)
    {
        ContractDetails details = contracts.get(contractId);
        if (details != null && details.isRentalContract() && validateRentalPeriod(details.getRentalStartDate(), newRentalEnddate))
        {
            details.setRentalEndDate(newRentalEnddate);
            details.rentVehicle.setAvailable(false);
            return true;
        }
        System.out.println("Invalid contract details!");
        return false;
    }

    /**
     * Calculates the total price of a rental contract based on the number of days rented and the daily price of the vehicle.
     * @param contractId Identifier of the rental contract.
     * @return the total price of the rental period, or -1 if the contract is not found or is not a rental contract.
     */
    public double getTotalPrice(int contractId)
    {
        ContractDetails details = contracts.get(contractId);
        if (details != null && details.isRentalContract())
        {
            long daysRented = ChronoUnit.DAYS.between(details.getRentalStartDate(), details.getRentalEndDate());
            return daysRented * details.getRentVehicle().getDailyPrice();
        }
        return -1;
    }

    /**
     * Retrieves the details of a rental contract in a formatted string.
     * @param contractId Identifier of the rental contract.
     * @return A string containing the rental contract details, or a message indicating no rental contract was found.
     */
    public String getRentalContractDetails(int contractId)
    {
        ContractDetails details = contracts.get(contractId);
        if (details != null && details.isRentalContract())
        {
            return "Rental Contract Details: \n" +
                    "Contract ID: " + contractId + "\n" +
                    "Customer: " + details.getCustomer().getFirstName() + " " + details.getCustomer().getLastName() + "\n" +
                    "Rent Vehicle: " + details.getRentVehicle().getRentVehicleDetails() + "\n" +
                    "Rental Start Date: " + details.getRentalStartDate() + "\n" +
                    "Rental End Date: " + details.getRentalEndDate();
        }
        return "No rental contract found with this ID.";
    }

    /**
     * Retrieves the details of a purchase contract in a formatted string.
     * @param contractId Identifier of the purchase contract.
     * @return A string containing the purchase contract details, or a message indicating no purchase contract was found.
     */
    public String getPurchaseContractDetails(int contractId)
    {
        ContractDetails details = contracts.get(contractId);
        if (details != null && !details.isRentalContract())
        {
            return "Purchase Contract Details: \n" +
                    "Contract ID: " + contractId + "\n" +
                    "Customer: " + details.getCustomer().getFirstName() + " " + details.getCustomer().getLastName() + "\n" +
                    "Sale Vehicle: " + details.getSaleVehicle().getSaleVehicleDetails();
        }
        return "No purchase contract found with this ID.";
    }


    /**
     * Validates the rental period ensuring the start date is not after the end date and not before the current date.
     * @param startDate The start date of the rental period.
     * @param endDate The end date of the rental period.
     * @return true if the rental period is valid, false otherwise.
     */
    public static boolean validateRentalPeriod(LocalDate startDate, LocalDate endDate)
    {
        return startDate != null           &&
               endDate   != null           &&
               !startDate.isAfter(endDate) &&
               !startDate.isBefore(LocalDate.now());
    }

    /* Inner class to hold contract details */
    private static class ContractDetails
    {
        private Customer customer;
        private SaleVehicle saleVehicle;
        private RentVehicle rentVehicle;
        private boolean isRentalContract;
        private LocalDate contractDate;
        private LocalDate rentalStartDate;
        private LocalDate rentalEndDate;

        public ContractDetails(Customer customer, SaleVehicle saleVehicle, RentVehicle rentVehicle, boolean isRentalContract,
                               LocalDate contractDate, LocalDate rentalStartdate, LocalDate rentalEnddate)
        {
            this.customer = customer;
            this.saleVehicle = saleVehicle;
            this.rentVehicle = rentVehicle;
            this.isRentalContract = isRentalContract;
            this.contractDate = contractDate;
            this.rentalStartDate = rentalStartdate;
            this.rentalEndDate = rentalEnddate;
        }

        /* Setter & Getter Methods of inner class-attributes */
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
}
