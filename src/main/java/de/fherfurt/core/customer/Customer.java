package de.fherfurt.core.customer;

import java.util.Date;

import de.fherfurt.logic.customerAddress.CustomerAddress;

/**
 * Represents a customer entity with personal information and address details.
 */
public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthdate;
    private boolean isFemale;
    private boolean isDeleted;
    private CustomerAddress customerAddress;

    /**
     * Creates a new Customer with the specified details.
     *
     * @param customerId      Unique identifier for the customer
     * @param firstName      Customer's first name
     * @param lastName       Customer's last name 
     * @param email         Customer's email address
     * @param birthdate     Customer's date of birth
     * @param isFemale      Customer's gender indicator
     * @param customerAddress Customer's address information
     */
    public Customer(int customerId, String firstName, String lastName, String email, Date birthdate, boolean isFemale, CustomerAddress customerAddress) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.isFemale = isFemale;
        this.isDeleted = false;
        this.customerAddress = customerAddress;
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

    public void setFemale(boolean isFemale) {
        this.isFemale = isFemale;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getDetails() {
        return "Customer ID: " + customerId + ", Name: " + firstName + " " + lastName + ", Email: " + email;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}