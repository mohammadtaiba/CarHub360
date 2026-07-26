package de.fherfurt.carhub360.customer;

import de.fherfurt.carhub360.customer.address.CustomerAddress;
import de.fherfurt.carhub360.customer.dto.CustomerAddressRequest;
import de.fherfurt.carhub360.customer.dto.CustomerRequest;

final class CustomerMapper {

    private CustomerMapper() {
    }

    static Customer toCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setBirthdate(request.getBirthdate());
        customer.setFemale(Boolean.TRUE.equals(request.getFemale()));
        customer.setCustomerAddress(toAddress(request.getAddress()));
        return customer;
    }

    private static CustomerAddress toAddress(CustomerAddressRequest request) {
        if (request == null) {
            return null;
        }
        CustomerAddress address = new CustomerAddress();
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());
        address.setStreet(request.getStreet());
        address.setStreetNumber(request.getStreetNumber());
        return address;
    }
}
