package de.fherfurt.service;

import de.fherfurt.core.entity.Customer;
import de.fherfurt.core.repository.CustomerRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class CustomerService {

    @Inject
    private CustomerRepository customerRepository;

    public List<Customer> findAllActive() {
        return customerRepository.findAllActive();
    }

    public Customer findActiveById(int customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null || customer.isDeleted()) {
            return null;
        }
        return customer;
    }

    public Customer create(Customer customer) {
        validateCustomer(customer);
        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            throw new IllegalArgumentException("A customer with this email already exists.");
        }
        customer.setDeleted(false);
        customerRepository.save(customer);
        return customer;
    }

    public Customer update(int customerId, Customer updatedCustomer) {
        Customer existing = findActiveById(customerId);
        if (existing == null) {
            return null;
        }

        validateCustomer(updatedCustomer);
        Customer duplicateEmail = customerRepository.findByEmail(updatedCustomer.getEmail());
        if (duplicateEmail != null && duplicateEmail.getCustomerId() != customerId) {
            throw new IllegalArgumentException("A customer with this email already exists.");
        }

        existing.setFirstName(updatedCustomer.getFirstName());
        existing.setLastName(updatedCustomer.getLastName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setBirthdate(updatedCustomer.getBirthdate());
        existing.setFemale(updatedCustomer.isFemale());
        existing.setCustomerAddress(updatedCustomer.getCustomerAddress());
        return customerRepository.update(existing);
    }

    public boolean softDelete(int customerId) {
        Customer customer = findActiveById(customerId);
        if (customer == null) {
            return false;
        }
        customer.setDeleted(true);
        customerRepository.update(customer);
        return true;
    }

    private void validateCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer payload is required.");
        }
        requireText(customer.getFirstName(), "firstName");
        requireText(customer.getLastName(), "lastName");
        requireText(customer.getEmail(), "email");
        if (!customer.getEmail().contains("@")) {
            throw new IllegalArgumentException("email must be a valid email address.");
        }
        if (customer.getBirthdate() == null) {
            throw new IllegalArgumentException("birthdate is required.");
        }
    }

    private void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
    }
}
