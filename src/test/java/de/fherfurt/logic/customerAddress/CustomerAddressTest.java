package de.fherfurt.logic.customerAddress;

import de.fherfurt.core.customer.CustomerManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class CustomerAddressTest {

    private CustomerAddress customerAddress;
    private CustomerAddressManager customerAddressManager;

    @Before
    public void setUp() {
        customerAddressManager = new CustomerAddressManager();
        customerAddress = new CustomerAddress(1, "Erfurt", "99097", "Straße", "22");
        customerAddressManager.updateCustomerAddress(1, "Erfurt", "99097", "Straße", "22");
        CustomerManager.createCustomer(1, "John", "Doe", "john.doe@example.com", new Date(), false, customerAddress);
    }

    @After
    public void tearDown() {
        customerAddress = null;
        customerAddressManager.updateCustomerAddress(1, "Erfurt", "99097", "Straße", "22");
    }

    @Test
    public void testUpdateCustomerAddress() {
        assertTrue(customerAddressManager.updateCustomerAddress(1, "Erfurt", "99097", "Neue Straße", "33"));
        assertFalse(customerAddressManager.updateCustomerAddress(0, "Erfurt", "99097", "Straße", "22"));
        assertFalse(customerAddressManager.updateCustomerAddress(1, "", "99097", "Straße", "22"));
        assertFalse(customerAddressManager.updateCustomerAddress(1, "Erfurt", "", "Straße", "22"));
        assertFalse(customerAddressManager.updateCustomerAddress(1, "Erfurt", "99097", "", "22"));
        assertFalse(customerAddressManager.updateCustomerAddress(1, "Erfurt", "99097", "Straße", ""));
    }

    @Test
    public void testGetCustomerAddressDetails() {
        customerAddressManager.updateCustomerAddress(1, "Erfurt", "99097", "Straße", "22");
        String details = customerAddressManager.getCustomerAddressDetails(1);
        assertNotNull(details);
        assertTrue(details.contains("Erfurt"));
        assertTrue(details.contains("99097"));
        assertTrue(details.contains("Straße"));
        assertTrue(details.contains("22"));
        assertFalse(details.contains("Leipzig"));
        assertFalse(details.contains("Gotha"));
    }

    @Test
    public void testGetCustomerAddressDetailsNotFound() {
        String details = customerAddressManager.getCustomerAddressDetails(999);
        assertEquals("Customer with ID 999 does not have an address.", details);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(1, customerAddress.getCustomerId());
        assertEquals("Erfurt", customerAddress.getCity());
        assertEquals("99097", customerAddress.getPostalCode());
        assertEquals("Straße", customerAddress.getStreet());
        assertEquals("22", customerAddress.getStreetNumber());

        customerAddress.setCustomerId(2);
        customerAddress.setCity("Leipzig");
        customerAddress.setPostalCode("04109");
        customerAddress.setStreet("Musterstraße");
        customerAddress.setStreetNumber("123");

        assertEquals(2, customerAddress.getCustomerId());
        assertEquals("Leipzig", customerAddress.getCity());
        assertEquals("04109", customerAddress.getPostalCode());
        assertEquals("Musterstraße", customerAddress.getStreet());
        assertEquals("123", customerAddress.getStreetNumber());
    }
}
