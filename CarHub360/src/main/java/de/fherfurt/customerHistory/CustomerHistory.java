package de.fherfurt.customerHistory;

import java.util.Date;


public class CustomerHistory {
    private int CustomerHistoryId;
    private int CustomerHistoryCustomerId;
    private int CustomerHistoryVehicleId;
    private de.fherfurt.CustomerHistoryReview CustomerHistoryReview ;
    private String Desriction;
    private Date ActionDate;
    private boolean IsforRentalCar;

    public int getCustomerHistoryId() {
        return CustomerHistoryId;
    }

    public void setCustomerHistoryId(int customerHistoryId) {
        CustomerHistoryId = customerHistoryId;
    }

    public int getCustomerHistoryCustomerId() {
        return CustomerHistoryCustomerId;
    }

    public void setCustomerHistoryCustomerId(int customerHistoryCustomerId) {
        CustomerHistoryCustomerId = customerHistoryCustomerId;
    }

    public int getCustomerHistoryVehicleId() {
        return CustomerHistoryVehicleId;
    }

    public void setCustomerHistoryVehicleId(int customerHistoryVehicleId) {
        CustomerHistoryVehicleId = customerHistoryVehicleId;
    }

    public de.fherfurt.CustomerHistoryReview getCustomerHistoryReview() {
        return CustomerHistoryReview;
    }

    public void setCustomerHistoryReview(de.fherfurt.CustomerHistoryReview customerHistoryReview) {
        CustomerHistoryReview = customerHistoryReview;
    }

    public String getDesriction() {
        return Desriction;
    }

    public void setDesriction(String desriction) {
        Desriction = desriction;
    }

    public Date getActionDate() {
        return ActionDate;
    }

    public void setActionDate(Date actionDate) {
        ActionDate = actionDate;
    }

    public boolean isIsforRentalCar() {
        return IsforRentalCar;
    }

    public void setIsforRentalCar(boolean isforRentalCar) {
        IsforRentalCar = isforRentalCar;
    }
}
