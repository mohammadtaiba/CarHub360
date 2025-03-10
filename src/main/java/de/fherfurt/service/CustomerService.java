package de.fherfurt.service;

import de.fherfurt.core.entity.Customer;
import de.fherfurt.core.entity.CustomerAddress;
import de.fherfurt.core.repository.CustomerRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.List;

/**
 * Manages customer operations such as creation, deletion, and retrieval,
 * now with persistent storage via CustomerRepository.
 */
@Stateless
public class CustomerService {

    @Inject
    private CustomerRepository customerRepository;

    /**
     * Creates and adds a new customer to the system if no customer exists with the same ID or email.
     */
    public boolean createCustomer(int customerId, String firstName, String lastName,
                                  String email, Date birthdate, boolean isFemale,
                                  CustomerAddress customerAddress) {

        // Prüfen, ob Kunde mit gleicher ID oder E-Mail existiert
        if (customerRepository.existsByIdOrEmail(customerId, email)) {
            return false;
        }

        // Neuen Customer anlegen und speichern
        Customer newCustomer = new Customer(
                customerId, firstName, lastName, email, birthdate, isFemale, customerAddress
        );
        customerRepository.save(newCustomer);
        return true;
    }

    /**
     * Removes a customer from the system based on their ID.
     */
    public boolean deleteCustomer(int customerId) {
        Customer existing = customerRepository.findById(customerId);
        if (existing != null) {
            customerRepository.delete(customerId);
            return true;
        }
        return false;
    }

    /**
     * Retrieves detailed information about a specific customer.
     */
    public String getCustomerDetails(int customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer != null) {
            return "Customer Details: \n" +
                    "Customer ID: " + customer.getCustomerId() + "\n" +
                    "First Name: " + customer.getFirstName() + "\n" +
                    "Last Name: " + customer.getLastName() + "\n" +
                    "Email: " + customer.getEmail() + "\n" +
                    "Birthdate: " + customer.getBirthdate() + "\n" +
                    "Is Female: " + customer.isFemale() + "\n" +
                    "Address: " + (customer.getCustomerAddress() != null
                    ? customer.getCustomerAddress().getStreet() + " "
                    + customer.getCustomerAddress().getStreetNumber() + ", "
                    + customer.getCustomerAddress().getCity() + " "
                    + customer.getCustomerAddress().getPostalCode()
                    : "No address set");
        }
        return "Customer with ID " + customerId + " was not found.";
    }

    /**
     * Retrieves the list of all customers from the DB.
     */
    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }
}
