package de.fherfurt.carhub360.customer;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class CustomerEmailUniquenessService {

    private static final String DUPLICATE_EMAIL = "A customer with this email already exists.";

    @Inject
    private CustomerRepository customerRepository;

    public void requireUniqueForCreate(String email) {
        if (customerRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException(DUPLICATE_EMAIL);
        }
    }

    public void requireUniqueForUpdate(int customerId, String email) {
        Customer duplicateEmail = customerRepository.findByEmail(email);
        if (duplicateEmail != null && duplicateEmail.getCustomerId() != customerId) {
            throw new IllegalArgumentException(DUPLICATE_EMAIL);
        }
    }
}
