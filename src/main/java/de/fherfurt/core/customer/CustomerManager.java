package de.fherfurt.core.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import de.fherfurt.logic.customerAddress.CustomerAddress;

/**
 * Manages customer operations such as creation, deletion, and retrieval.
 */
public class CustomerManager {
    private static List<Customer> customers = new ArrayList<>();

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
        Customer customerToRemove = customers.stream()
            .filter(customer -> customer.getCustomerId() == customerId)
            .findFirst()
            .orElse(null);
        
        if (customerToRemove != null) {
            customers.remove(customerToRemove);
            return true;
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

    /**
     * Retrieves the list of all customers.
     *
     * @return a list of customers
     */
    public static List<Customer> getCustomerList() {
        return customers;
    }
}