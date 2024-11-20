package de.fherfurt.logic.customerAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides business logic for managing customer addresses.
 */
public class CustomerAddressManager {
    private List<CustomerAddress> customerAddresses = new ArrayList<>();

    /**
     * Updates or creates a new address for a customer.
     * If customer exists, updates their address details.
     * If customer doesn't exist, creates a new address entry.
     *
     * @param customerId   Customer's unique identifier
     * @param city         Customer's city
     * @param postalCode   Customer's postal code
     * @param street       Customer's street name
     * @param streetNumber Customer's street number
     * @return true if update/creation successful, false if invalid parameters
     */
    public boolean updateCustomerAddress(int customerId, String city, String postalCode, String street,
                                         String streetNumber) {
        if (customerId      <= 0    || city == null             || city.isEmpty() ||
            postalCode      == null || postalCode.isEmpty()     ||
            street          == null || street.isEmpty()         ||
            streetNumber    == null || streetNumber.isEmpty()) {
            return false;
        }

        for (CustomerAddress address : customerAddresses) {
            if (address.getCustomerId() == customerId) {
                address.setCity(city);
                address.setPostalCode(postalCode);
                address.setStreet(street);
                address.setStreetNumber(streetNumber);
                return true;
            }
        }

        CustomerAddress newAddress = new CustomerAddress(customerId, city, postalCode, street, streetNumber);
        customerAddresses.add(newAddress);
        return true;
    }


    /**
     * Retrieves formatted address details for a specific customer.
     *
     * @param customerId Customer's unique identifier
     * @return Formatted string with address details if found, error message if not found
     */
    public String getCustomerAddressDetails(int customerId) {
        for (CustomerAddress address : customerAddresses) {
            if (address.getCustomerId() == customerId) {
                return "Customer Address details for ID " + customerId + ":\n"
                        + "City: " + address.getCity() + "\n"
                        + "Postal Code: " + address.getPostalCode() + "\n"
                        + "Street: " + address.getStreet() + "\n"
                        + "Street Number: " + address.getStreetNumber();
            }
        }
        return "Customer with ID " + customerId + " does not have an address.";
    }
}
