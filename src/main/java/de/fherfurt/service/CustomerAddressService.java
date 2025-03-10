package de.fherfurt.service;

import de.fherfurt.core.entity.CustomerAddress;
import de.fherfurt.core.repository.CustomerAddressRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Provides business logic for managing customer addresses using persistent storage.
 */
@Stateless
public class CustomerAddressService {

    @Inject
    private CustomerAddressRepository repository;

    /**
     * Updates or creates a new address for a customer.
     * If an address with the same customerId exists, updates it.
     * Otherwise, creates a new address entry.
     *
     * @param customerId   Customer's unique identifier
     * @param city         Customer's city
     * @param postalCode   Customer's postal code
     * @param street       Customer's street name
     * @param streetNumber Customer's street number
     * @return true if update/creation successful, false if invalid parameters
     */
    public boolean updateCustomerAddress(int customerId, String city, String postalCode,
                                         String street, String streetNumber) {
        // Parameter-Validierung
        if (customerId <= 0 || city == null || city.isEmpty() ||
                postalCode == null || postalCode.isEmpty() ||
                street == null || street.isEmpty() ||
                streetNumber == null || streetNumber.isEmpty()) {
            return false;
        }

        // Prüfen, ob Address bereits existiert
        CustomerAddress address = repository.findById(customerId);
        if (address != null) {
            // Update
            address.setCity(city);
            address.setPostalCode(postalCode);
            address.setStreet(street);
            address.setStreetNumber(streetNumber);
            repository.update(address);
        } else {
            // Neu anlegen
            CustomerAddress newAddress = new CustomerAddress(customerId, city, postalCode, street, streetNumber);
            repository.save(newAddress);
        }
        return true;
    }

    /**
     * Retrieves formatted address details for a specific customer.
     *
     * @param customerId Customer's unique identifier
     * @return Formatted string with address details if found, error message if not found
     */
    public String getCustomerAddressDetails(int customerId) {
        CustomerAddress address = repository.findById(customerId);
        if (address != null) {
            return "Customer Address details for ID " + customerId + ":\n"
                    + "City: " + address.getCity() + "\n"
                    + "Postal Code: " + address.getPostalCode() + "\n"
                    + "Street: " + address.getStreet() + "\n"
                    + "Street Number: " + address.getStreetNumber();
        }
        return "Customer with ID " + customerId + " does not have an address.";
    }

    /**
     * Beispiel: Alle Adressen aus der DB holen.
     */
    public List<CustomerAddress> getAllAddresses() {
        return repository.findAll();
    }
}
