package de.fherfurt.carhub360.customer;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class CustomerReferenceService {

    @Inject
    private CustomerRepository customerRepository;

    public Customer requireActiveCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null || customer.isDeleted()) {
            throw new IllegalArgumentException("customerId does not reference an active customer.");
        }
        return customer;
    }
}
