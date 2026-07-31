package de.fherfurt.carhub360.customer.address;

import de.fherfurt.carhub360.customer.address.dto.CustomerAddressRequest;
import de.fherfurt.carhub360.customer.address.dto.CustomerAddressResponse;
import java.util.List;

final class CustomerAddressMapper {

    private CustomerAddressMapper() {
    }

    static CustomerAddress toAddress(CustomerAddressRequest request) {
        CustomerAddress address = new CustomerAddress();
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());
        address.setStreet(request.getStreet());
        address.setStreetNumber(request.getStreetNumber());
        return address;
    }

    static List<CustomerAddressResponse> toResponses(List<CustomerAddress> addresses) {
        return addresses.stream()
                .map(CustomerAddressMapper::toResponse)
                .toList();
    }

    static CustomerAddressResponse toResponse(CustomerAddress address) {
        CustomerAddressResponse response = new CustomerAddressResponse();
        response.setAddressId(address.getAddressId());
        response.setCity(address.getCity());
        response.setPostalCode(address.getPostalCode());
        response.setStreet(address.getStreet());
        response.setStreetNumber(address.getStreetNumber());
        return response;
    }
}
