package de.fherfurt.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import de.fherfurt.logic.CustomerAddress;

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

    private static List<Customer> customers = new ArrayList<>();

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

    /**
     * Creates and adds a new customer to the system if no customer exists with the same ID or email.
     *
     * @param customerId      Unique identifier for the customer
     * @param firstName      Customer's first name
     * @param lastName       Customer's last name
     * @param email         Customer's email address
     * @param birthdate     Customer's date of birth
     * @param isFemale      Customer's gender indicator
     * @param customerAddress Customer's address information
     * @return true if customer was successfully created, false if customer with ID or email already exists
     */
    public static boolean createCustomer(int customerId, String firstName, String lastName, String email, Date birthdate, boolean isFemale, CustomerAddress customerAddress) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId || customer.getEmail().equals(email)) {
                return false;
            }
        }
        Customer newCustomer = new Customer(customerId, firstName, lastName, email, birthdate, isFemale, customerAddress);
        customers.add(newCustomer);
        return true;
    }

    /**
     * Removes a customer from the system based on their ID.
     *
     * @param customerId The ID of the customer to delete
     * @return true if customer was found and deleted, false otherwise
     */
    public static boolean deleteCustomer(int customerId) {
        Iterator<Customer> iterator = customers.iterator();
        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            if (customer.getCustomerId() == customerId) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves detailed information about a specific customer.
     *
     * @param customerId The ID of the customer to retrieve
     * @return formatted string containing customer details if found, error message if not found
     */
    public static String getCustomerDetails(int customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                return "Customer Details: \n" +
                        "Customer ID: " + customer.getCustomerId() + "\n" +
                        "First Name: " + customer.getFirstName() + "\n" +
                        "Last Name: " + customer.getLastName() + "\n" +
                        "Email: " + customer.getEmail() + "\n" +
                        "Birthdate: " + customer.getBirthdate() + "\n" +
                        "Is Female: " + customer.isFemale() + "\n" +
                        "Address: " + customer.getCustomerAddress().getStreet() + " " + customer.getCustomerAddress().getStreetNumber() + ", " + customer.getCustomerAddress().getCity() + " " + customer.getCustomerAddress().getPostalCode();
            }
        }
        return "Customer with ID " + customerId + " was not found.";
    }

    public String getDetails() {
        return "Customer ID: " + customerId + ", Name: " + firstName + " " + lastName + ", Email: " + email;
    }

    public static List<Customer> getCustomerList() {
        return new ArrayList<>(customers);
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

}
