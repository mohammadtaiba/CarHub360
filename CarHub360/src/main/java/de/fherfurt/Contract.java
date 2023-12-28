package de.fherfurt;

import java.util.Date;

public class Contract
{
    /* Methods */
    public void createPurchaseContract(int contractID, Customer customer, SaleVehicle saleVehicle)
    {
        setContractId(contractID);
        System.out.println("Vertragsnummer: ");
    }

    public void createRentalContract(int contractID, Customer customer, RentVehicle rentVehicle)
    {
        setContractId(contractID);
        rentVehicle.setAvailable(false);
        rentVehicle.setDeposit(rentVehicle.getT);    }

    public void terminateRentalContract(int ContractID)
    {
        // Logic to terminate a rental contract
    }

    public void renewRentalContract(int ContractID)
    {
        // Logic to renew a rental contract
    }

    public void getRentalContractDetails(int ContractID)
    {
        // Logic to get rental contract details
    }

    public void getPurchaseContractDetails(int ContractID)
    {
        // Logic to get purchase contract details
    }


    /* Attributes */
    private Customer m_Customer;
    private SaleVehicle m_SaleVehicle;
    private RentVehicle m_RentVehicle;
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


}
