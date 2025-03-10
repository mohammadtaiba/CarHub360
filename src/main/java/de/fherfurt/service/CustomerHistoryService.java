package de.fherfurt.service;

import de.fherfurt.core.entity.Customer;
import de.fherfurt.core.entity.Vehicle;
import de.fherfurt.core.entity.CustomerHistory;
import de.fherfurt.core.entity.utils.CustomerHistoryReview;
import de.fherfurt.core.repository.CustomerHistoryRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.Date;

/**
 * Provides business logic for managing a collection of customer history records,
 * now with persistent storage (DB) instead of a static list.
 */
@Stateless
public class CustomerHistoryService {

    @Inject
    private CustomerHistoryRepository repository;

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
    public boolean createOrUpdateCustomerHistory(int customerHistoryId,
                                                 Customer customer,
                                                 Vehicle customerHistoryVehicle,
                                                 CustomerHistoryReview customerHistoryReview,
                                                 String description,
                                                 Date actionDate,
                                                 boolean isForRentalCar) {
        // Parameter-Check
        if (customerHistoryId < 0 || customer == null || customerHistoryVehicle == null
                || customerHistoryReview == null || description == null || actionDate == null) {
            return false;
        }

        // Prüfen, ob ein Datensatz mit dieser ID bereits existiert
        CustomerHistory existing = repository.findById(customerHistoryId);
        if (existing != null) {
            // Update
            existing.setCustomer(customer);
            existing.setCustomerHistoryVehicle(customerHistoryVehicle);
            existing.setCustomerHistoryReview(customerHistoryReview);
            existing.setDescription(description);
            existing.setActionDate(actionDate);
            existing.setForRentalCar(isForRentalCar);
            repository.update(existing);
        } else {
            // Neu anlegen
            CustomerHistory newHistory = new CustomerHistory(
                    customerHistoryId,
                    customer,
                    customerHistoryVehicle,
                    customerHistoryReview,
                    description,
                    actionDate,
                    isForRentalCar
            );
            repository.save(newHistory);
        }
        return true;
    }

    /**
     * Retrieves a customer history record by its ID.
     */
    public CustomerHistory getCustomerHistory(int customerHistoryId) {
        return repository.findById(customerHistoryId);
    }

    /**
     * Retrieves the review associated with a specific customer history record.
     */
    public CustomerHistoryReview getCustomerFinalReview(int customerHistoryId) {
        CustomerHistory history = repository.findById(customerHistoryId);
        return (history != null) ? history.getCustomerHistoryReview() : null;
    }
}
