package de.fherfurt.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import de.fherfurt.customerAddress.CustomerAddress;
public class Customer {
    /**
     * The CustomerDetails class represents the details of a customer.
     * Each customer detail object includes attributes such as ID, name, email, birthdate, gender, and address.
     */
    public class CustomerDetails {
        private int customerId;
        private String firstName;
        private String lastName;
        private String email;
        private Date birthdate;
        private boolean isFemale;
        private boolean isDeleted;
        private CustomerAddress customerAddress; // composition
        // Constructor and getter/setter methods
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

    private List<CustomerDetails> customers = new ArrayList<>();
    /**
     * Creates a new customer record.
     *
     * @param customerId The unique ID of the customer.
     * @param firstName  The first name of the customer.
     * @param lastName   The last name of the customer.
     * @param email      The email address of the customer.
     * @param birthdate  The birthdate of the customer.
     * @param isFemale   Indicates whether the customer is female.
     * @return True if the customer record is successfully created, false otherwise.
     */
    public boolean createCustomer(int customerId, String firstName, String lastName, String email, Date birthdate, boolean isFemale) {
        for (CustomerDetails customer : customers) {
            if (customer.getCustomerId() == customerId) {
                return false;
            }
            if (customer.getEmail().equals(email)) {
                return false;
            }
        }
        CustomerDetails newCustomer = new CustomerDetails(customerId, firstName, lastName, email, birthdate, isFemale);
        customers.add(newCustomer);
        return true;
    }
    /**
     * Deletes an existing customer record.
     *
     * @param customerId The ID of the customer to be deleted.
     * @return True if the customer record is successfully deleted, false otherwise.
     */
    public boolean deleteCustomer(int customerId) {
        for (CustomerDetails customer : customers) {
            if (customer.getCustomerId() == customerId) {
                customers.remove(customer);
                return true;
            }
        }
        return false;
    }
    /**
     * Retrieves the details of a customer.
     *
     * @param customerId The ID of the customer whose details are to be retrieved.
     * @return A string containing the details of the customer if found, or a message indicating that the customer was not found.
     */
    public String getCustomerDetails(int customerId) {
        for (CustomerDetails customer : customers) {
            if (customer.getCustomerId() == customerId) {
                String customerDetails = "Customer Details: \n" +
                        "Customer ID: " + customer.getCustomerId() + "\n" +
                        "First Name: " + customer.getFirstName() + "\n" +
                        "Last Name: " + customer.getLastName() + "\n" +
                        "Email: " + customer.getEmail() + "\n" +
                        "Birthdate: " + customer.getBirthdate() + "\n" +
                        "Is Female: " + customer.isFemale();
                return customerDetails;
            }
        }
        return "Customer with ID " + customerId + " was not found.";
    }

}