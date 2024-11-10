package de.fherfurt.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages customer addresses with attributes like customer ID, city, postal code, street and street number.
 */
public class CustomerAddress {
    private int customerId;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private static List<CustomerAddress> customerAddresses = new ArrayList<>();

    public CustomerAddress(int customerId, String city, String postalCode, String street, String streetNumber) {
        this.customerId = customerId;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Updates or creates a new address for a customer.
     * If customer exists, updates their address details.
     * If customer doesn't exist, creates new address entry.
     * 
     * @param customerId   Customer's unique identifier
     * @param city         Customer's city
     * @param postalCode   Customer's postal code
     * @param street       Customer's street name
     * @param streetNumber Customer's street number
     * @return true if update/creation successful, false if invalid parameters
     */
    public static boolean updateCustomerAddress(int customerId, String city, String postalCode, String street, String streetNumber) {
        if (customerId >= 0 && city != null && postalCode != null && street != null && streetNumber != null) {
            for (CustomerAddress address : customerAddresses) {
                if (address.getCustomerId() == customerId) {
                    address.setCity(city);
                    address.setPostalCode(postalCode);
                    address.setStreet(street);
                    address.setStreetNumber(streetNumber);
                    return true;
                }
                else {
                    return false;
                }
            }

            CustomerAddress addressDetails = new CustomerAddress(customerId, city, postalCode, street, streetNumber);
            customerAddresses.add(addressDetails);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves formatted address details for a specific customer.
     * 
     * @param customerId Customer's unique identifier
     * @return Formatted string with address details if found, error message if not found
     */
    public static String getCustomerAddressDetails(int customerId) {
        for (CustomerAddress addressDetails : customerAddresses) {
            if (addressDetails.getCustomerId() == customerId) {
                return "Customer Address details for ID " + customerId + ":\n"
                        + "City: " + addressDetails.getCity() + "\n"
                        + "Postal Code: " + addressDetails.getPostalCode() + "\n"
                        + "Street: " + addressDetails.getStreet() + "\n"
                        + "Street Number: " + addressDetails.getStreetNumber();
            }
        }
        return "Customer with ID " + customerId + " does not have an address.";
    }
}
