package de.fherfurt.customerAddress;

import java.util.ArrayList;
import java.util.List;

public class CustomerAddress {

    private class CustomerAddressDetails {

        private int customerId;
        private String city;
        private String postalCode;
        private String street;
        private String streetNumber;
        // Constructor and getter/setter methods
        public CustomerAddressDetails(int customerId, String city, String postalCode, String street, String streetNumber) {
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

    }

    private List<CustomerAddressDetails> customerAddresses = new ArrayList<>();
    /**
     * Updates the address details of a customer.
     *
     * @param customerId   The ID of the customer whose address is to be updated.
     * @param city         The city of the customer's address.
     * @param postalCode   The postal code of the customer's address.
     * @param street       The street of the customer's address.
     * @param streetNumber The street number of the customer's address.
     * @return True if the address details are successfully updated, false otherwise.
     */
    public boolean updateCustomerAddress(int customerId, String city, String postalCode, String street, String streetNumber) {
        if (customerId >= 0 && city != null && postalCode != null && street != null && streetNumber != null) {
            CustomerAddressDetails addressDetails = new CustomerAddressDetails(customerId, city, postalCode, street, streetNumber);
            addressDetails.setCustomerId(customerId);
            addressDetails.setCity(city);
            addressDetails.setPostalCode(postalCode);
            addressDetails.setStreet(street);
            addressDetails.setStreetNumber(streetNumber);

            customerAddresses.add(addressDetails);

            return true;
        } else {
            return false;
        }
    }
    /**
     * Retrieves the address details of a customer.
     *
     * @param customerId The ID of the customer whose address details are to be retrieved.
     * @return A string containing the address details of the customer if found, or a message indicating that the customer does not have an address.
     */
    public String getCustomerAddressDetails(int customerId) {
        for (CustomerAddressDetails addressDetails : customerAddresses) {
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
