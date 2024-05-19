package de.fherfurt.customer;

import de.fherfurt.customerAddress.CustomerAddress;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class CustomerTest {

    private CustomerAddress address;
    private Customer customer;
    private Date birthdate;

    @Before
    public void setUp() {
        address = new CustomerAddress(123, "Erfurt", "99084", "Main Street", "123");
        birthdate = new Date();
        customer = new Customer(1, "John", "Doe", "john.doe@example.com", birthdate, false, address);
        Customer.createCustomer(1, "John", "Doe", "john.doe@example.com", birthdate, false, address);
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
        assertEquals(address, customer.getCustomerAddress());
    }

    @Test
    public void testSetCustomerAddress() {
        CustomerAddress newAddress = new CustomerAddress(123, "99085", "Second Street", "Erfurt","456");
        customer.setCustomerAddress(newAddress);
        assertEquals(newAddress, customer.getCustomerAddress());
    }

    @Test
    public void testCreateCustomer() {
        boolean created = Customer.createCustomer(2, "Alice", "Johnson", "alice.johnson@example.com", birthdate, true, address);
        assertTrue(created);
        assertEquals(2, Customer.getCustomerList().size());
    }

    @Test
    public void testCreateCustomerWithDuplicateIdOrEmail() {
        boolean createdWithDuplicateId = Customer.createCustomer(1, "Bob", "Marley", "bob.marley@example.com", birthdate, false, address);
        assertFalse(createdWithDuplicateId);

        boolean createdWithDuplicateEmail = Customer.createCustomer(3, "Charlie", "Brown", "john.doe@example.com", birthdate, false, address);
        assertFalse(createdWithDuplicateEmail);
    }

    @Test
    public void testDeleteCustomer() {
        // Ensure the customer to be deleted is actually in the list
        assertNotNull(Customer.getCustomerDetails(1));

        // Perform deletion
        boolean deleted = Customer.deleteCustomer(1);
        assertTrue(deleted);
        assertEquals(1, Customer.getCustomerList().size());
    }


    @Test
    public void testDeleteNonExistentCustomer() {
        boolean deleted = Customer.deleteCustomer(999);
        assertFalse(deleted);
    }

    @Test
    public void testGetCustomerDetails() {
        String details = Customer.getCustomerDetails(1);
        assertNotNull(details);
        assertTrue(details.contains("Customer ID: 1"));
        assertTrue(details.contains("First Name: John"));
        assertTrue(details.contains("Last Name: Doe"));
        assertTrue(details.contains("Email: john.doe@example.com"));
    }

    @Test
    public void testGetNonExistentCustomerDetails() {
        String details = Customer.getCustomerDetails(999);
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
