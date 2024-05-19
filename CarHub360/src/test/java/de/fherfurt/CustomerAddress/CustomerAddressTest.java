package de.fherfurt.CustomerAddress;

import de.fherfurt.customerAddress.CustomerAddress;
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
        CustomerAddress.updateCustomerAddress(1, "Erfurt", "99097", "Straße", "22"); // Resetting state
    }

    @Test
    public void testUpdateCustomerAddress() {
        assertTrue(CustomerAddress.updateCustomerAddress(1, "Erfurt", "99097", "Neue Straße", "33"));
        assertFalse(CustomerAddress.updateCustomerAddress(-1, "Leipzig", "54780", "AndereStraße", "48"));
        assertFalse(CustomerAddress.updateCustomerAddress(1, null, "13579", "BahnStraße", "66"));
        assertFalse(CustomerAddress.updateCustomerAddress(2, "Gotha", "53791", "AndereStraße2", null));
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
}
