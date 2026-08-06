package de.fherfurt.carhub360.customer.address;

import de.fherfurt.carhub360.shared.validation.RequiredText;
import jakarta.ejb.Stateless;

@Stateless
public class CustomerAddressValidator {

    public void validate(CustomerAddress address) {
        if (address == null) {
            throw new IllegalArgumentException("Address payload is required.");
        }
        RequiredText.require(address.getCity(), "city");
        RequiredText.require(address.getPostalCode(), "postalCode");
        RequiredText.require(address.getStreet(), "street");
        RequiredText.require(address.getStreetNumber(), "streetNumber");
    }
}
