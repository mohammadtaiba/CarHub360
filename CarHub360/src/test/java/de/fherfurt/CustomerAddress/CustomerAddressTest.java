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
        assertTrue(customerAddress.updateCustomerAddress(1, "City1", "12345", "Street1", "1A"));
        assertFalse(customerAddress.updateCustomerAddress(-1, "City2", "67890", "Street2", "2B"));
        assertFalse(customerAddress.updateCustomerAddress(1, null, "67890", "Street2", "2B"));
        assertFalse(customerAddress.updateCustomerAddress(2, "City3", "67890", "Street3", null));
    }

    @Test
    public void testGetCustomerAddressDetails() {
        customerAddress.updateCustomerAddress(1, "City1", "12345", "Street1", "1A");
        String details = customerAddress.getCustomerAddressDetails(1);
        assertNotNull(details);
        assertTrue(details.contains("City1"));
        assertFalse(details.contains("City2"));
        assertFalse(details.contains("City3"));
    }
}

