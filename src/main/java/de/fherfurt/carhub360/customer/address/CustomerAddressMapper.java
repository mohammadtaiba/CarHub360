package de.fherfurt.carhub360.customer.address;

import de.fherfurt.carhub360.customer.dto.CustomerAddressRequest;

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
}
