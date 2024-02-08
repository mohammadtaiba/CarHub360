package de.fherfurt.customerAddress;

import java.util.HashMap;
import java.util.Map;

public class CustomerAddress {

    private class CustomerAddressDetails {

        private int CustomerId;
        private String City;
        private String PostalCode;
        private String Street;
        private String StreetNumber;

        public CustomerAddressDetails(int customerId, String city, String postalCode, String street, String streetNumber) {
            this.CustomerId = customerId;
            this.City = city;
            this.PostalCode = postalCode;
            this.Street = street;
            this.StreetNumber = streetNumber;
        }


        public int getCustomerId() {
            return CustomerId;
        }
        public void setCustomerId(int customerId) {
            this.CustomerId = customerId;
        }

        public String getCity() {
            return City;
        }
        public void setCity(String city) {
            this.City = city;
        }

        public String getPostalCode() {
            return PostalCode;
        }
        public void setPostalCode(String postalCode) {
            this.PostalCode = postalCode;
        }

        public String getStreet() {
            return Street;
        }
        public void setStreet(String street) {
            this.Street = street;
        }

        public String getStreetNumber() {
            return StreetNumber;
        }
        public void setStreetNumber(String streetNumber) {
            this.StreetNumber = streetNumber;
        }

    }

    private Map<Integer, CustomerAddressDetails> customerAddresses = new HashMap<>();

    public boolean updateCustomerAddress(int customerId, String city, String postalCode, String street, String streetNumber) {
        if (customerId >= 0 && city != null && postalCode != null && street != null && streetNumber != null) {
            CustomerAddressDetails addressDetails = new CustomerAddressDetails(customerId,city,postalCode, street, streetNumber);
            addressDetails.setCustomerId(customerId);
            addressDetails.setCity(city);
            addressDetails.setPostalCode(postalCode);
            addressDetails.setStreet(street);
            addressDetails.setStreetNumber(streetNumber);

            customerAddresses.put(customerId, addressDetails);

            return true;
        } else {
            return false;
        }
    }

    public String getCustomerAddressDetails(int customerId) {
        if (customerAddresses.containsKey(customerId)) {
            CustomerAddressDetails addressDetails = customerAddresses.get(customerId);


            return "Customer Address details for ID " + customerId + ":\n"
                    + "City: " + addressDetails.getCity() + "\n"
                    + "Postal Code: " + addressDetails.getPostalCode() + "\n"
                    + "Street: " + addressDetails.getStreet() + "\n"
                    + "Street Number: " + addressDetails.getStreetNumber();
        } else {
            return "Customer with ID " + customerId + " does not have an address.";
        }
    }
}