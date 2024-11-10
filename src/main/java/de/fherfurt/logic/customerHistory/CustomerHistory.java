package de.fherfurt.logic.customerHistory;

import de.fherfurt.core.Customer;
import de.fherfurt.model.Vehicle;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Maintains the history of customer actions including reviews, vehicle interactions and rental information.
 * Each history record contains details about the customer, vehicle, review, description and timing of actions.
 */
public class CustomerHistory {

    private int customerHistoryId;
    private Customer customer;
    private Vehicle customerHistoryVehicle;
    private CustomerHistoryReview customerHistoryReview;
    private String description;
    private Date actionDate;
    private boolean isForRentalCar;
    private List<CustomerHistory> customerHistoryList = new ArrayList<>();

    /**
     * Creates a new customer history record with the specified details.
     *
     * @param customerHistoryId      Unique identifier for this history record
     * @param customer               Associated customer
     * @param customerHistoryVehicle Vehicle involved in the action
     * @param customerHistoryReview  Customer's review
     * @param description            Description of the action
     * @param actionDate            Date when action occurred
     * @param isForRentalCar        Whether this involves a rental vehicle
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

    /**
     * Creates or updates a customer history record with the provided information.
     * If a record with the given ID exists, it will be updated. Otherwise, a new record is created.
     * All parameters must be valid (non-null where applicable and positive ID) for successful creation.
     *
     * @param customerHistoryId      Unique identifier for this history record
     * @param customer               Associated customer
     * @param customerHistoryVehicle Vehicle involved in the action
     * @param customerHistoryReview  Customer's review
     * @param description            Description of the action
     * @param actionDate            Date when action occurred
     * @param isForRentalCar        Whether this involves a rental vehicle
     * @return true if creation/update successful, false if parameters invalid
     */
    public boolean createCustomerHistory(int customerHistoryId, Customer customer,
                                         Vehicle customerHistoryVehicle, CustomerHistoryReview customerHistoryReview,
                                         String description, Date actionDate, boolean isForRentalCar) {
        if (customerHistoryId >= 0 && customer != null && customerHistoryVehicle != null
                && customerHistoryReview != null && description != null && actionDate != null) {

            CustomerHistory newCustomerHistory = new CustomerHistory(customerHistoryId, customer,
                    customerHistoryVehicle, customerHistoryReview, description, actionDate, isForRentalCar);

            for (CustomerHistory history : customerHistoryList) {
                if (history.getCustomerHistoryId() == customerHistoryId) {
                    history.setCustomer(customer);
                    history.setCustomerHistoryVehicle(customerHistoryVehicle);
                    history.setCustomerHistoryReview(customerHistoryReview);
                    history.setDescription(description);
                    history.setActionDate(actionDate);
                    history.setForRentalCar(isForRentalCar);
                    return true;
                }
            }

            customerHistoryList.add(newCustomerHistory);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves a customer history record by its ID.
     *
     * @param customerHistoryId ID of the history record to retrieve
     * @return CustomerHistory object if found, null otherwise
     */
    public CustomerHistory getCustomerHistory(int customerHistoryId) {
        for (CustomerHistory history : customerHistoryList) {
            if (history.getCustomerHistoryId() == customerHistoryId) {
                return history;
            }
        }
        return null;
    }

    /**
     * Retrieves the review associated with a specific customer history record.
     *
     * @param customerHistoryId ID of the history record
     * @return CustomerHistoryReview if history exists, null otherwise
     */
    public CustomerHistoryReview getCustomerFinalReview(int customerHistoryId) {
        CustomerHistory history = getCustomerHistory(customerHistoryId);
        if (history != null) {
            return history.getCustomerHistoryReview();
        } else {
            return null;
        }
    }
}
