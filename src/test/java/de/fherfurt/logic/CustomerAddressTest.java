package de.fherfurt.logic;

import de.fherfurt.logic.CustomerAddress;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerAddressTest {

    private CustomerAddress customerAddress;

    @Before
    public void setUp() {
        customerAddress = new CustomerAddress(1, "Erfurt", "99097", "Straße", "22");
        CustomerAddress.updateCustomerAddress(1, "Erfurt", "99097", "Straße", "22");
    }

    @After
    public void tearDown() {
        customerAddress = null;
        CustomerAddress.updateCustomerAddress(1, "Erfurt", "99097", "Straße", "22");
    }

    @Test
    public void testUpdateCustomerAddress() {
        assertTrue(CustomerAddress.updateCustomerAddress(1, "Erfurt", "99097", "Neue Straße", "33"));
        assertFalse(CustomerAddress.updateCustomerAddress(2, "Leipzig", "54780", "AndereStraße", "48"));
        assertFalse(CustomerAddress.updateCustomerAddress(3, null, "13579", "BahnStraße", "66"));
        assertFalse(CustomerAddress.updateCustomerAddress(4, "Gotha", "53791", "AndereStraße2", null));
    }

    @Test
    public void testGetCustomerAddressDetails() {
        CustomerAddress.updateCustomerAddress(1, "Erfurt", "99097", "Straße", "22");
        String details = CustomerAddress.getCustomerAddressDetails(1);
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
        String details = CustomerAddress.getCustomerAddressDetails(999);
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
