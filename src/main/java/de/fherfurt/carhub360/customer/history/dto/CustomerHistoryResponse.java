package de.fherfurt.carhub360.customer.history.dto;

import de.fherfurt.carhub360.customer.history.CustomerHistoryReview;
import java.util.Date;

public class CustomerHistoryResponse {

    private int customerHistoryId;
    private int customerId;
    private int vehicleId;
    private CustomerHistoryReview customerHistoryReview;
    private String description;
    private Date actionDate;
    private boolean forRentalCar;

    public int getCustomerHistoryId() {
        return customerHistoryId;
    }

    public void setCustomerHistoryId(int customerHistoryId) {
        this.customerHistoryId = customerHistoryId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public CustomerHistoryReview getCustomerHistoryReview() {
        return customerHistoryReview;
    }

    public void setCustomerHistoryReview(CustomerHistoryReview customerHistoryReview) {
        this.customerHistoryReview = customerHistoryReview;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public boolean isForRentalCar() {
        return forRentalCar;
    }

    public void setForRentalCar(boolean forRentalCar) {
        this.forRentalCar = forRentalCar;
    }
}
