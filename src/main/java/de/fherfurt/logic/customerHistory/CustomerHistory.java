package de.fherfurt.logic.customerHistory;

import de.fherfurt.core.customer.Customer;
import de.fherfurt.model.vehicle.Vehicle;

import java.util.Date;

/**
 * Represents a single customer history record, including details about actions,
 * vehicles, reviews, and other related information.
 */
public class CustomerHistory {

    private int customerHistoryId;
    private Customer customer;
    private Vehicle customerHistoryVehicle;
    private CustomerHistoryReview customerHistoryReview;
    private String description;
    private Date actionDate;
    private boolean isForRentalCar;

    /**
     * Constructs a CustomerHistory object with the specified details.
     *
     * @param customerHistoryId      Unique identifier for this history record
     * @param customer               Associated customer
     * @param customerHistoryVehicle Vehicle involved in the action
     * @param customerHistoryReview  Customer's review
     * @param description            Description of the action
     * @param actionDate             Date when action occurred
     * @param isForRentalCar         Whether this involves a rental vehicle
     */
    public CustomerHistory(int customerHistoryId, Customer customer, Vehicle customerHistoryVehicle,
                           CustomerHistoryReview customerHistoryReview, String description,
                           Date actionDate, boolean isForRentalCar) {
        this.customerHistoryId = customerHistoryId;
        this.customer = customer;
        this.customerHistoryVehicle = customerHistoryVehicle;
        this.customerHistoryReview = customerHistoryReview;
        this.description = description;
        this.actionDate = actionDate;
        this.isForRentalCar = isForRentalCar;
    }

    public int getCustomerHistoryId() {
        return customerHistoryId;
    }

    public void setCustomerHistoryId(int customerHistoryId) {
        this.customerHistoryId = customerHistoryId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getCustomerHistoryVehicle() {
        return customerHistoryVehicle;
    }

    public void setCustomerHistoryVehicle(Vehicle customerHistoryVehicle) {
        this.customerHistoryVehicle = customerHistoryVehicle;
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
        return isForRentalCar;
    }

    public void setForRentalCar(boolean forRentalCar) {
        isForRentalCar = forRentalCar;
    }
}
