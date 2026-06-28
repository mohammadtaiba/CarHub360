package de.fherfurt.service;

import de.fherfurt.core.entity.CustomerAddress;
import de.fherfurt.core.repository.CustomerAddressRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class CustomerAddressService {

    @Inject
    private CustomerAddressRepository repository;

    public List<CustomerAddress> findAll() {
        return repository.findAll();
    }

    public CustomerAddress findById(int addressId) {
        return repository.findById(addressId);
    }

    public CustomerAddress create(CustomerAddress address) {
        validate(address);
        repository.save(address);
        return address;
    }

    public CustomerAddress update(int addressId, CustomerAddress address) {
        CustomerAddress existing = repository.findById(addressId);
        if (existing == null) {
            return null;
        }
        validate(address);
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

    private void validate(CustomerAddress address) {
        if (address == null) {
            throw new IllegalArgumentException("Address payload is required.");
        }
        requireText(address.getCity(), "city");
        requireText(address.getPostalCode(), "postalCode");
        requireText(address.getStreet(), "street");
        requireText(address.getStreetNumber(), "streetNumber");
    }

    private void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
    }
}
