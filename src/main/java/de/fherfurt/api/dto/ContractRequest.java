package de.fherfurt.api.dto;

import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class ContractRequest {

    @Positive
    private int customerId;

    private Integer saleVehicleId;
    private Integer rentVehicleId;
    private boolean rentalContract;
    private LocalDate contractDate;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Integer getSaleVehicleId() {
        return saleVehicleId;
    }

    public void setSaleVehicleId(Integer saleVehicleId) {
        this.saleVehicleId = saleVehicleId;
    }

    public Integer getRentVehicleId() {
        return rentVehicleId;
    }

    public void setRentVehicleId(Integer rentVehicleId) {
        this.rentVehicleId = rentVehicleId;
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
