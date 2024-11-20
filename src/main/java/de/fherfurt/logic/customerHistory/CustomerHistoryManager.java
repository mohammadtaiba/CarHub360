package de.fherfurt.logic.customerHistory;

import de.fherfurt.core.customer.Customer;
import de.fherfurt.model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Provides business logic for managing a collection of customer history records.
 */
public class CustomerHistoryManager {

    private List<CustomerHistory> customerHistoryList = new ArrayList<>();

    /**
     * Creates or updates a customer history record with the provided information.
     * If a record with the given ID exists, it will be updated. Otherwise, a new record is created.
     *
     * @param customerHistoryId      Unique identifier for this history record
     * @param customer               Associated customer
     * @param customerHistoryVehicle Vehicle involved in the action
     * @param customerHistoryReview  Customer's review
     * @param description            Description of the action
     * @param actionDate             Date when action occurred
     * @param isForRentalCar         Whether this involves a rental vehicle
     * @return true if creation/update successful, false if parameters are invalid
     */
    public boolean createOrUpdateCustomerHistory(int customerHistoryId, Customer customer,
                                                 Vehicle customerHistoryVehicle, CustomerHistoryReview customerHistoryReview,
                                                 String description, Date actionDate, boolean isForRentalCar) {
        if (customerHistoryId < 0 || customer == null || customerHistoryVehicle == null
                || customerHistoryReview == null || description == null || actionDate == null) {
            return false;
        }

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

        CustomerHistory newHistory = new CustomerHistory(customerHistoryId, customer,
                customerHistoryVehicle, customerHistoryReview, description, actionDate, isForRentalCar);
        customerHistoryList.add(newHistory);
        return true;
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
        return (history != null) ? history.getCustomerHistoryReview() : null;
    }
}
