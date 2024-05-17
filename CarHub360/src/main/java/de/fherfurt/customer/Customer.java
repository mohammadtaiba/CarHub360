package de.fherfurt.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import de.fherfurt.customerAddress.CustomerAddress;

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

    public static boolean deleteCustomer(int customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                customers.remove(customer);
                return true;
            }
        }
        return false;
    }

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
}
