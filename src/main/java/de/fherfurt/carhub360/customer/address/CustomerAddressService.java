package de.fherfurt.carhub360.customer.address;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class CustomerAddressService {

    @Inject
    private CustomerAddressRepository repository;

    @Inject
    private CustomerAddressValidator customerAddressValidator;

    public List<CustomerAddress> findAll() {
        return repository.findAll();
    }

    public CustomerAddress findById(int addressId) {
        return repository.findById(addressId);
    }

    public CustomerAddress create(CustomerAddress address) {
        customerAddressValidator.validate(address);
        repository.save(address);
        return address;
    }

    public CustomerAddress update(int addressId, CustomerAddress address) {
        CustomerAddress existing = repository.findById(addressId);
        if (existing == null) {
            return null;
        }
        customerAddressValidator.validate(address);
        existing.setCity(address.getCity());
        existing.setPostalCode(address.getPostalCode());
        existing.setStreet(address.getStreet());
        existing.setStreetNumber(address.getStreetNumber());
        return repository.update(existing);
    }

    public boolean delete(int addressId) {
        if (repository.findById(addressId) == null) {
            return false;
        }
        repository.delete(addressId);
        return true;
    }
}
