package de.fherfurt.carhub360.customer;

import de.fherfurt.carhub360.shared.validation.RequiredText;
import jakarta.ejb.Stateless;

@Stateless
public class CustomerValidator {

    public void validate(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer payload is required.");
        }
        RequiredText.require(customer.getFirstName(), "firstName");
        RequiredText.require(customer.getLastName(), "lastName");
        RequiredText.require(customer.getEmail(), "email");
        if (!customer.getEmail().contains("@")) {
            throw new IllegalArgumentException("email must be a valid email address.");
        }
        if (customer.getBirthdate() == null) {
            throw new IllegalArgumentException("birthdate is required.");
        }
    }
}
