package de.fherfurt.customerHistory;

import de.fherfurt.customer.Customer;
import de.fherfurt.Vehicle.Vehicle;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CustomerHistory {

    private int customerHistoryId;
    private Customer customer;
    private Vehicle customerHistoryVehicle;
    private CustomerHistoryReview customerHistoryReview;
    private String description;
    private Date actionDate;
    private boolean isForRentalCar;
    private List<CustomerHistory> customerHistoryList = new ArrayList<>();



    // Parameterized constructor
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
     * Creates a new customer history record.
     *
     * @param customerHistoryId        The unique ID of the customer history record.
     * @param customer                 The customer associated with the action.
     * @param customerHistoryVehicle   The vehicle involved in the action.
     * @param customerHistoryReview    The review associated with the action.
     * @param description              A description of the action.
     * @param actionDate               The date when the action occurred.
     * @param isForRentalCar           Indicates whether the action pertains to a rental car.
     * @return True if the customer history record is successfully created, false otherwise.
     */
    public boolean createCustomerHistory(int customerHistoryId, Customer customer,
                                         Vehicle customerHistoryVehicle, CustomerHistoryReview customerHistoryReview,
                                         String description, Date actionDate, boolean isForRentalCar) {
        if (customerHistoryId >= 0 && customer != null && customerHistoryVehicle != null
                && customerHistoryReview != null && description != null && actionDate != null) {

            CustomerHistory newCustomerHistory = new CustomerHistory(customerHistoryId, customer,
                    customerHistoryVehicle, customerHistoryReview, description, actionDate, isForRentalCar);

            // Check if customer history already exists and update it
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

            // If customer history does not exist, add a new history record
            customerHistoryList.add(newCustomerHistory);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves the customer history record associated with the specified ID.
     *
     * @param customerHistoryId The ID of the customer history record to retrieve.
     * @return The customer history record if found, null otherwise.
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
     * Retrieves the final review associated with the specified customer history ID.
     *
     * @param customerHistoryId The ID of the customer history record.
     * @return The final review associated with the customer history if found, null otherwise.
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
