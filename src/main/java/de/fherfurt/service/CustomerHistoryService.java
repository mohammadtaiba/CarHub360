package de.fherfurt.service;

import de.fherfurt.core.entity.Customer;
import de.fherfurt.core.entity.CustomerHistory;
import de.fherfurt.core.entity.Vehicle;
import de.fherfurt.core.entity.utils.CustomerHistoryReview;
import de.fherfurt.core.repository.CustomerHistoryRepository;
import de.fherfurt.core.repository.CustomerRepository;
import de.fherfurt.core.repository.VehicleRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.List;

@Stateless
public class CustomerHistoryService {

    @Inject
    private CustomerHistoryRepository repository;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private VehicleRepository vehicleRepository;

    public List<CustomerHistory> findAll() {
        return repository.findAll();
    }

    public List<CustomerHistory> findByCustomerId(int customerId) {
        return repository.findByCustomerId(customerId);
    }

    public CustomerHistory findById(int customerHistoryId) {
        return repository.findById(customerHistoryId);
    }

    public CustomerHistory create(int customerId,
                                  int vehicleId,
                                  CustomerHistoryReview review,
                                  String description,
                                  Date actionDate,
                                  boolean forRentalCar) {
        Customer customer = requireActiveCustomer(customerId);
        Vehicle vehicle = requireVehicle(vehicleId);
        validate(review, description, actionDate);
        CustomerHistory history = new CustomerHistory(
                0,
                customer,
                vehicle,
                review,
                description,
                actionDate,
                forRentalCar
        );
        repository.save(history);
        return history;
    }

    public CustomerHistory update(int customerHistoryId,
                                  int customerId,
                                  int vehicleId,
                                  CustomerHistoryReview review,
                                  String description,
                                  Date actionDate,
                                  boolean forRentalCar) {
        CustomerHistory existing = repository.findById(customerHistoryId);
        if (existing == null) {
            return null;
        }
        Customer customer = requireActiveCustomer(customerId);
        Vehicle vehicle = requireVehicle(vehicleId);
        validate(review, description, actionDate);
        existing.setCustomer(customer);
        existing.setCustomerHistoryVehicle(vehicle);
        existing.setCustomerHistoryReview(review);
        existing.setDescription(description);
        existing.setActionDate(actionDate);
        existing.setForRentalCar(forRentalCar);
        return repository.update(existing);
    }

    public boolean delete(int customerHistoryId) {
        if (repository.findById(customerHistoryId) == null) {
            return false;
        }
        repository.delete(customerHistoryId);
        return true;
    }

    private Customer requireActiveCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null || customer.isDeleted()) {
            throw new IllegalArgumentException("customerId does not reference an active customer.");
        }
        return customer;
    }

    private Vehicle requireVehicle(int vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId);
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicleId does not reference an existing vehicle.");
        }
        return vehicle;
    }

    private void validate(CustomerHistoryReview review, String description, Date actionDate) {
        if (review == null) {
            throw new IllegalArgumentException("customerHistoryReview is required.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("description is required.");
        }
        if (actionDate == null) {
            throw new IllegalArgumentException("actionDate is required.");
        }
    }
}
