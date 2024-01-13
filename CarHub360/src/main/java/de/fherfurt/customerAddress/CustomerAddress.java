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

        public int getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(int customerId) {
            CustomerId = customerId;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String city) {
            City = city;
        }

        public String getPostalCode() {
            return PostalCode;
        }

        public void setPostalCode(String postalCode) {
            PostalCode = postalCode;
        }

        public String getStreet() {
            return Street;
        }

        public void setStreet(String street) {
            Street = street;
        }

        public String getStreetNumber() {
            return StreetNumber;
        }

        public void setStreetNumber(String streetNumber) {
            StreetNumber = streetNumber;
        }


    }

    private Map<Integer, CustomerAddressDetails> customerAddresses = new HashMap<>();

    public boolean UpdateCustomerAddress(int CustomerId, int City, String PostalCode, String Street,String StreetNumber){

        if (!customerAddresses.containsKey(CustomerId)) {
        if (CustomerId >= 0 && City != null && PostalCode != null && Street != null && StreetNumber != null) {
            // Update the customer address
            this.CustomerId = CustomerId;
            this.City = City;
            this.PostalCode = PostalCode;
            this.Street = Street;
            this.StreetNumber = StreetNumber;

            System.out.println("Customer address updated successfully.");
        } else {
            System.out.println("Invalid customer address details. Update failed.");

        }
        return false;
        } else {
            System.out.println("CustomerId is taken");
            return false;
        }
    }
}}
