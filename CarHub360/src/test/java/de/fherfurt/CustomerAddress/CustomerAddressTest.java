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
        customerAddress = new CustomerAddress();
    }

    @After
    public void tearDown() {
        customerAddress = null;
    }

    @Test
    public void testUpdateCustomerAddress() {
        assertTrue(customerAddress.updateCustomerAddress(1, "Erfurt", "99097", "Straße", "22"));
        assertFalse(customerAddress.updateCustomerAddress(-1, "Leipzig", "54780", "AndereStraße", "48"));
        assertFalse(customerAddress.updateCustomerAddress(1, null, "13579", "BahnStraße", "66"));
        assertFalse(customerAddress.updateCustomerAddress(2, "Gotha", "53791", "AndereStraße2", null));
    }

    @Test
    public void testGetCustomerAddressDetails() {
        customerAddress.updateCustomerAddress(1, "Erfurt", "99097", "Straße", "22");
        String details = customerAddress.getCustomerAddressDetails(1);
        assertNotNull(details);
        assertTrue(details.contains("Erfurt"));
        assertFalse(details.contains("Leipzig"));
        assertFalse(details.contains("Gotha"));
    }
}

