package de.fherfurt.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contractId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_vehicle_id")
    private SaleVehicle saleVehicle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rent_vehicle_id")
    private RentVehicle rentVehicle;

    @Column(nullable = false)
    private boolean rentalContract;

    @NotNull
    @Column(nullable = false)
    private LocalDate contractDate;

    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;

    public Contract() {
    }

    public Contract(int contractId,
                    Customer customer,
                    SaleVehicle saleVehicle,
                    RentVehicle rentVehicle,
                    boolean rentalContract,
                    LocalDate contractDate,
                    LocalDate rentalStartDate,
                    LocalDate rentalEndDate) {
        this.contractId = contractId;
        this.customer = customer;
        this.saleVehicle = saleVehicle;
        this.rentVehicle = rentVehicle;
        this.rentalContract = rentalContract;
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
        return rentalContract;
    }

    public void setRentalContract(boolean rentalContract) {
        this.rentalContract = rentalContract;
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
