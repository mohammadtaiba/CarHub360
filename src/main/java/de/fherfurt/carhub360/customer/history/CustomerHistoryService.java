package de.fherfurt.carhub360.customer.history;

import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.customer.CustomerRepository;
import de.fherfurt.carhub360.vehicle.Vehicle;
import de.fherfurt.carhub360.vehicle.VehicleRepository;
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

    @Inject
    private CustomerHistoryValidator customerHistoryValidator;

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
        customerHistoryValidator.validate(review, description, actionDate);
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
        customerHistoryValidator.validate(review, description, actionDate);
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
}
