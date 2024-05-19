package de.fherfurt.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import de.fherfurt.customerAddress.CustomerAddress;
/**
 * This class represents a customer, including attributes such as customer ID, first name, last name, email, birthdate,
 * gender, deletion status, and customer address.
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
     * Parameterized constructor to initialize customer attributes.
     *
     * @param customerId     The unique ID of the customer.
     * @param firstName      The first name of the customer.
     * @param lastName       The last name of the customer.
     * @param email          The email address of the customer.
     * @param birthdate      The birthdate of the customer.
     * @param isFemale       Indicates whether the customer is female.
     * @param customerAddress The address of the customer.
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
     * Creates a new customer.
     *
     * @param customerId     The unique ID of the customer.
     * @param firstName      The first name of the customer.
     * @param lastName       The last name of the customer.
     * @param email          The email address of the customer.
     * @param birthdate      The birthdate of the customer.
     * @param isFemale       Indicates whether the customer is female.
     * @param customerAddress The address of the customer.
     * @return True if the customer is successfully created, false if a customer with the same ID or email already exists.
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
     * Deletes a customer.
     *
     * @param customerId The unique ID of the customer to be deleted.
     * @return True if the customer is successfully deleted, false otherwise.
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
     * Retrieves the details of a customer.
     *
     * @param customerId The unique ID of the customer.
     * @return A string containing the details of the customer if found, or a message indicating that the customer was not found.
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
    /**
     * Retrieves the basic details of the customer.
     *
     * @return A string containing the basic details of the customer.
     */
    public String getDetails() {
        return "Customer ID: " + customerId + ", Name: " + firstName + " " + lastName + ", Email: " + email;
    }

    /**
     * Retrieves the list of all customers.
     *
     * @return List<Customer> The list of customers.
     */
    public static List<Customer> getCustomerList() {
        return new ArrayList<>(customers); // Return a new list to avoid external modifications.
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

}
