package de.fherfurt.core.customer;

import de.fherfurt.logic.customerAddress.CustomerAddress;
import de.fherfurt.core.customer.CustomerManager;
import de.fherfurt.core.customer.Customer;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CustomerTest {

    private Customer customer;
    private CustomerAddress address;
    private CustomerManager customerManager;
    private Date birthdate;
    private SimpleDateFormat sdf;

    @Before
    public void setUp() throws ParseException {
        CustomerManager.getCustomerList().clear();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        address = new CustomerAddress(123, "Erfurt", "99084", "Main Street", "123");
        birthdate = sdf.parse("02/02/1990");
        customerManager = new CustomerManager();
        customer = new Customer(1, "John", "Doe", "john.doe@example.com", birthdate, false, address);
    }

    @After
    public void tearDown() {
        CustomerManager.getCustomerList().clear();
    }

    @Test
    public void testGetCustomerId() {
        assertEquals(1, customer.getCustomerId());
    }

    @Test
    public void testSetCustomerId() {
        customer.setCustomerId(2);
        assertEquals(2, customer.getCustomerId());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("John", customer.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        customer.setFirstName("Jane");
        assertEquals("Jane", customer.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Doe", customer.getLastName());
    }

    @Test
    public void testSetLastName() {
        customer.setLastName("Smith");
        assertEquals("Smith", customer.getLastName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("john.doe@example.com", customer.getEmail());
    }

    @Test
    public void testSetEmail() {
        customer.setEmail("jane.smith@example.com");
        assertEquals("jane.smith@example.com", customer.getEmail());
    }

    @Test
    public void testGetBirthdate() {
        assertEquals(birthdate, customer.getBirthdate());
    }

    @Test
    public void testSetBirthdate() {
        Date newBirthdate = new Date();
        customer.setBirthdate(newBirthdate);
        assertEquals(newBirthdate, customer.getBirthdate());
    }

    @Test
    public void testIsFemale() {
        assertFalse(customer.isFemale());
    }

    @Test
    public void testSetFemale() {
        customer.setFemale(true);
        assertTrue(customer.isFemale());
    }

    @Test
    public void testIsDeleted() {
        assertFalse(customer.isDeleted());
    }

    @Test
    public void testSetDeleted() {
        customer.setDeleted(true);
        assertTrue(customer.isDeleted());
    }

    @Test
    public void testGetCustomerAddress() {
        assertNotNull(customer.getCustomerAddress());
    }

    @Test
    public void testSetCustomerAddress() {
        CustomerAddress newAddress = new CustomerAddress(123, "99085", "Second Street", "Erfurt","456");
        customer.setCustomerAddress(newAddress);
        assertEquals(newAddress, customer.getCustomerAddress());
    }

    @Test
    public void testCreateCustomer() {
        int initialSize = customerManager.getCustomerList().size();
        
        boolean created = CustomerManager.createCustomer(4, "Alice", "Johnson", "alice.johnson@example.com", birthdate, true, address);
        
        System.out.println("Customer created: " + created);
        
        assertTrue(created);
        assertEquals(initialSize + 1, customerManager.getCustomerList().size());
    }

    @Test
    public void testCreateCustomerWithDuplicateIdOrEmail() {
        boolean createdFirst = CustomerManager.createCustomer(1, "John", "Doe", "john.doe@example.com", birthdate, false, address);
        assertTrue(createdFirst);

        boolean createdWithDuplicateId = CustomerManager.createCustomer(1, "Bob", "Marley", "bob.marley@example.com", birthdate, false, address);
        assertFalse(createdWithDuplicateId);

        boolean createdWithDuplicateEmail = CustomerManager.createCustomer(2, "Charlie", "Brown", "john.doe@example.com", birthdate, false, address);
        assertFalse(createdWithDuplicateEmail);
    }

    @Test
    public void testDeleteCustomer() {
        customerManager.createCustomer(1, "John", "Doe", "john.doe@example.com", birthdate, false, address);
        
        assertEquals(1, customerManager.getCustomerList().size());
        
        boolean deleted = customerManager.deleteCustomer(1);
        assertTrue(deleted);
        
        assertEquals(0, customerManager.getCustomerList().size());
    }

    @Test
    public void testDeleteNonExistentCustomer() {
        boolean deleted = customerManager.deleteCustomer(999);
        assertFalse(deleted);
    }

    @Test
    public void testGetCustomerDetails() {
        CustomerManager.createCustomer(1, "John", "Doe", "john.doe@example.com", birthdate, false, address);
        
        String details = CustomerManager.getCustomerDetails(1);

        String expectedDetails = "Customer Details: \n" +
                "Customer ID: 1\n" +
                "First Name: John\n" +
                "Last Name: Doe\n" +
                "Email: john.doe@example.com\n" +
                "Birthdate: " + birthdate.toString() + "\n" +
                "Is Female: false\n" +
                "Address: Main Street 123, Erfurt 99084";

        assertEquals(expectedDetails, details);
    }


    @Test
    public void testGetNonExistentCustomerDetails() {
        String details = customerManager.getCustomerDetails(999);
        assertEquals("Customer with ID 999 was not found.", details);
    }

    @Test
    public void testGetDetails() {
        String details = customer.getDetails();
        assertNotNull(details);
        assertTrue(details.contains("Customer ID: 1"));
        assertTrue(details.contains("Name: John Doe"));
        assertTrue(details.contains("Email: john.doe@example.com"));
    }
}