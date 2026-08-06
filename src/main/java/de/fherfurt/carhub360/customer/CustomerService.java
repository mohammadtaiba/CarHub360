package de.fherfurt.carhub360.customer;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class CustomerService {

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private CustomerValidator customerValidator;

    @Inject
    private CustomerEmailUniquenessService customerEmailUniquenessService;

    @Inject
    private CustomerProfileUpdater customerProfileUpdater;

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
        customerValidator.validate(customer);
        customerEmailUniquenessService.requireUniqueForCreate(customer.getEmail());
        customer.setDeleted(false);
        customerRepository.save(customer);
        return customer;
    }

    public Customer update(int customerId, Customer updatedCustomer) {
        Customer existing = findActiveById(customerId);
        if (existing == null) {
            return null;
        }

        customerValidator.validate(updatedCustomer);
        customerEmailUniquenessService.requireUniqueForUpdate(customerId, updatedCustomer.getEmail());

        customerProfileUpdater.apply(existing, updatedCustomer);
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
}
