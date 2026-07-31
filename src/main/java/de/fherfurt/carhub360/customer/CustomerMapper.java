package de.fherfurt.carhub360.customer;

import de.fherfurt.carhub360.customer.address.CustomerAddress;
import de.fherfurt.carhub360.customer.address.dto.CustomerAddressRequest;
import de.fherfurt.carhub360.customer.address.dto.CustomerAddressResponse;
import de.fherfurt.carhub360.customer.dto.CustomerRequest;
import de.fherfurt.carhub360.customer.dto.CustomerResponse;
import java.util.List;

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

    static List<CustomerResponse> toResponses(List<Customer> customers) {
        return customers.stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    static CustomerResponse toResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setCustomerId(customer.getCustomerId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setBirthdate(customer.getBirthdate());
        response.setFemale(customer.isFemale());
        response.setDeleted(customer.isDeleted());
        response.setCustomerAddress(toAddressResponse(customer.getCustomerAddress()));
        response.setFullName(customer.getFullName());
        return response;
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

    private static CustomerAddressResponse toAddressResponse(CustomerAddress address) {
        if (address == null) {
            return null;
        }
        CustomerAddressResponse response = new CustomerAddressResponse();
        response.setAddressId(address.getAddressId());
        response.setCity(address.getCity());
        response.setPostalCode(address.getPostalCode());
        response.setStreet(address.getStreet());
        response.setStreetNumber(address.getStreetNumber());
        return response;
    }
}
