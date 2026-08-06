package de.fherfurt.carhub360.customer;

import jakarta.ejb.Stateless;

@Stateless
public class CustomerProfileUpdater {

    public void apply(Customer existing, Customer updatedCustomer) {
        existing.setFirstName(updatedCustomer.getFirstName());
        existing.setLastName(updatedCustomer.getLastName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setBirthdate(updatedCustomer.getBirthdate());
        existing.setFemale(updatedCustomer.isFemale());
        existing.setCustomerAddress(updatedCustomer.getCustomerAddress());
    }
}
