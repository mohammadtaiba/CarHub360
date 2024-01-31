package de.fherfurt.customer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.*;


public class CustomerTest {

    private Customer customer;

    @Before
    public void setUp() {
        customer = new Customer();
    }

    @After
    public void tearDown() {
        customer = null;
    }

    @Test
    public void testCreateCustomer() {
        assertTrue(customer.createCustomer(1, "John", "Doe", "john.doe@example.com", new Date(), true));
        assertFalse(customer.createCustomer(1, "Jane", "Doe", "jane.doe@example.com", new Date(), false));
    }

    @Test
    public void testDeleteCustomer() {
        customer.createCustomer(2, "Jane", "Doe", "jane.doe@example.com", new Date(), false);
        assertTrue(customer.deleteCustomer(2));
        assertFalse(customer.deleteCustomer(3));
    }

    @Test
    public void testGetCustomerDetails() {
        customer.createCustomer(3, "Bob", "Smith", "bob.smith@example.com", new Date(), true);
        String details = customer.getCustomerDetails(3);
        assertNotNull(details);
        assertTrue(details.contains("Bob"));
        assertFalse(details.contains("Alice"));
    }
}
