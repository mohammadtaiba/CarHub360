package de.fherfurt;

import java.util.Date;

public class Contract
{

    /* Attributes */
    private int ContractId;
    private int ContractCustomerId;
    private int ContractVehicleId;
    private boolean IsRentalContract;
    private Date ContractSaleDate;
    private Date RentalStartDate;
    private Date RentalEndDate;


    /* Set & Get Methods */
    public int getContractId() {
        return ContractId;
    }

    public void setContractId(int contractId) {
        ContractId = contractId;
    }

    public int getContractCustomerId() {
        return ContractCustomerId;
    }

    public void setContractCustomerId(int contractCustomerId) {
        ContractCustomerId = contractCustomerId;
    }

    public int getContractVehicleId() {
        return ContractVehicleId;
    }

    public void setContractVehicleId(int contractVehicleId) {
        ContractVehicleId = contractVehicleId;
    }

    public boolean isRentalContract() {
        return IsRentalContract;
    }

    public void setRentalContract(boolean rentalContract) {
        IsRentalContract = rentalContract;
    }

    public Date getContractSaleDate() {
        return ContractSaleDate;
    }

    public void setContractSaleDate(Date contractSaleDate) {
        ContractSaleDate = contractSaleDate;
    }

    public Date getRentalStartDate() {
        return RentalStartDate;
    }

    public void setRentalStartDate(Date rentalStartDate) {
        RentalStartDate = rentalStartDate;
    }

    public Date getRentalEndDate() {
        return RentalEndDate;
    }

    public void setRentalEndDate(Date rentalEndDate) {
        RentalEndDate = rentalEndDate;
    }


    /* Methods */
    public void CreatePurchaseContract(int ContractID, int ContractCustomerId, int ContractVehicleId, Date ContractSaleDate) {
        // Logic to create a purchase contract
    }

    public void CreateRentalContract(int ContractID, int ContractCustomerId, int ContractVehicleId, Date RentalStartdate, Date RentalEnddate) {
        // Logic to create a rental contract
    }

    public void TerminateRentalContract(int ContractID) {
        // Logic to terminate a rental contract
    }

    public void RenewRentalContract(int ContractID) {
        // Logic to renew a rental contract
    }

    public ContractDetails GetRentalContractDetails(int ContractID) {
        // Logic to get rental contract details
        return null; // Placeholder return
    }

    public ContractDetails GetPurchaseContractDetails(int ContractID) {
        // Logic to get purchase contract details
        return null; // Placeholder return
    }

}
