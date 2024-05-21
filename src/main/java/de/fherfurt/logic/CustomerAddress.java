package de.fherfurt.logic;

import java.util.ArrayList;
import java.util.List;
/**
 * This class manages customer addresses, including attributes such as customer ID, city, postal code, street, and street number.
 */
public class CustomerAddress {
    private int customerId;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private static List<CustomerAddress> customerAddresses = new ArrayList<>();

    /**
     * Parameterized constructor to initialize customer address attributes.
     *
     * @param customerId   The unique ID of the customer.
     * @param city         The city of the customer's address.
     * @param postalCode   The postal code of the customer's address.
     * @param street       The street of the customer's address.
     * @param streetNumber The street number of the customer's address.
     */
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
     * Updates the address details for a customer.
     *
     * @param customerId   The unique ID of the customer.
     * @param city         The city of the customer's address.
     * @param postalCode   The postal code of the customer's address.
     * @param street       The street of the customer's address.
     * @param streetNumber The street number of the customer's address.
     * @return True if the address details are successfully updated, false otherwise.
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
     * Retrieves the details of a customer's address.
     *
     * @param customerId The unique ID of the customer.
     * @return A string containing the customer's address details if found, or a message indicating that the customer does not have an address.
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
