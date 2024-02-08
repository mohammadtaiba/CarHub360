package de.fherfurt.customer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import de.fherfurt.customerAddress.CustomerAddress;
public class Customer {

    public class CustomerDetails {
        private int customerId;
        private String firstName;
        private String lastName;
        private String email;
        private Date birthdate;
        private boolean isFemale;
        private boolean isDeleted;
        private CustomerAddress customerAddress; // composition

        public CustomerDetails(int customerId, String firstName, String lastName, String email, Date birthdate, boolean isFemale) {
            this.customerId = customerId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.birthdate = birthdate;
            this.isFemale = isFemale;
            this.isDeleted = false;
            this.customerAddress = new CustomerAddress();
        }

        public int getCustomerId() {
            return customerId;
        }
        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public Date getBirthdate() {
            return birthdate;
        }
        public void setBirthdate(Date birthdate) {
            this.birthdate = birthdate;
        }

        public boolean isFemale() {
            return isFemale;
        }
        public void setFemale(boolean female) {
            this.isFemale = female;
        }

        public boolean isDeleted() {
            return isDeleted;
        }
        public void setDelete(boolean deleted) {
            this.isDeleted = deleted;
        }

        public CustomerAddress getCustomerAddress() {
            return customerAddress;
        }

    }

    private Map<Integer, CustomerDetails> customers = new HashMap<>();


    public boolean createCustomer(int customerId, String firstName, String lastName, String email, Date birthdate, boolean isFemale) {
        if (!customers.containsKey(customerId)) {
            CustomerDetails newCustomer = new CustomerDetails(customerId, firstName, lastName, email, birthdate, isFemale);
            customers.put(customerId, newCustomer);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteCustomer(int customerId) {
        if (customers.containsKey(customerId)) {
            customers.remove(customerId);
            return true;
        } else {
            return false;
        }
    }

    public String getCustomerDetails(int customerId) {
        if (customers.containsKey(customerId)) {
            CustomerDetails customer = customers.get(customerId);

            String customerDetails = "Customer Details: \n" +
                    "Customer ID: " + customer.getCustomerId() + "\n" +
                    "First Name: " + customer.getFirstName() + "\n" +
                    "Last Name: " + customer.getLastName() + "\n" +
                    "Email: " + customer.getEmail() + "\n" +
                    "Birthdate: " + customer.getBirthdate() + "\n" +
                    "Is Female: " + customer.isFemale();


            return customerDetails;
        } else {
            return "Customer with ID " + customerId + " was not found.";
        }
    }
}

