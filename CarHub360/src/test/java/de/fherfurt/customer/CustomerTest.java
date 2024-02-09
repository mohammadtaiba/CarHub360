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
        assertTrue(customer.createCustomer(1, "Helmut", "Mustermann",
                "HelmutDerMann@mail.de", new Date(), true));
        assertFalse(customer.createCustomer(1, "Elena", "Mustermann",
                "kokosnuss@gmail.com", new Date(), false));
        assertFalse(customer.createCustomer(2, "Elena", "Musterfrau",
                "HelmutDerMann@mail.de", new Date(), false));
    }

    @Test
    public void testDeleteCustomer() {
        customer.createCustomer(2, "Günter", "Bob", "NerdGünter@email.com", new Date(), false);
        assertTrue(customer.deleteCustomer(2));
        assertFalse(customer.deleteCustomer(3));
    }

    @Test
    public void testGetCustomerDetails() {
        customer.createCustomer(3, "Sheng", "Wang", "tucktuck@gmail.com", new Date(), true);
        String details = customer.getCustomerDetails(3);
        assertNotNull(details);
        assertTrue(details.contains("Sheng"));
        assertFalse(details.contains("Alina"));
    }
}
