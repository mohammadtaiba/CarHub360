package de.fherfurt.carhub360.customer;

import de.fherfurt.carhub360.customer.address.CustomerAddressMapper;
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
        customer.setCustomerAddress(request.getAddress() == null
                ? null
                : CustomerAddressMapper.toAddress(request.getAddress()));
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
        response.setCustomerAddress(customer.getCustomerAddress() == null
                ? null
                : CustomerAddressMapper.toResponse(customer.getCustomerAddress()));
        response.setFullName(customer.getFullName());
        return response;
    }
}
