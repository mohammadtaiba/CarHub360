package de.fherfurt.carhub360.customer.history;

import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.customer.CustomerReferenceService;
import de.fherfurt.carhub360.vehicle.Vehicle;
import de.fherfurt.carhub360.vehicle.VehicleReferenceService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.Date;
import java.util.List;

@Stateless
public class CustomerHistoryService {

    @Inject
    private CustomerHistoryRepository repository;

    @Inject
    private CustomerReferenceService customerReferenceService;

    @Inject
    private VehicleReferenceService vehicleReferenceService;

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
        Customer customer = customerReferenceService.requireActiveCustomer(customerId);
        Vehicle vehicle = vehicleReferenceService.requireVehicle(vehicleId);
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
        Customer customer = customerReferenceService.requireActiveCustomer(customerId);
        Vehicle vehicle = vehicleReferenceService.requireVehicle(vehicleId);
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
}
