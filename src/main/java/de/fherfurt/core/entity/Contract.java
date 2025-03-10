package de.fherfurt.core.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * This class represents a contract, including attributes such as contract ID, customer, sale vehicle, rent vehicle,
 * contract type, contract date, rental start date, and rental end date.
 */
@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    private int contractId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "sale_vehicle_id")
    private SaleVehicle saleVehicle;

    @ManyToOne
    @JoinColumn(name = "rent_vehicle_id")
    private RentVehicle rentVehicle;

    private boolean isRentalContract;
    private LocalDate contractDate;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;

    /**
     * Parameterloser Konstruktor (für JPA erforderlich).
     */
    public Contract() {
    }

    /**
     * Parameterized constructor to initialize contract attributes.
     *
     * @param contractId       The unique ID of the contract.
     * @param customer         The customer associated with the contract.
     * @param saleVehicle      The vehicle involved in the sale contract.
     * @param rentVehicle      The vehicle involved in the rental contract.
     * @param isRentalContract Indicates whether the contract is a rental contract.
     * @param contractDate     The date of the contract.
     * @param rentalStartDate  The start date of the rental period.
     * @param rentalEndDate    The end date of the rental period.
     */
    public Contract(int contractId,
                    Customer customer,
                    SaleVehicle saleVehicle,
                    RentVehicle rentVehicle,
                    boolean isRentalContract,
                    LocalDate contractDate,
                    LocalDate rentalStartDate,
                    LocalDate rentalEndDate) {
        this.contractId = contractId;
        this.customer = customer;
        this.saleVehicle = saleVehicle;
        this.rentVehicle = rentVehicle;
        this.isRentalContract = isRentalContract;
        this.contractDate = contractDate;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }

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
