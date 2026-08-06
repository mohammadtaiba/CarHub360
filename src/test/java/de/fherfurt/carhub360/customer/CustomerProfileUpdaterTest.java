package de.fherfurt.carhub360.customer;

import de.fherfurt.carhub360.customer.address.CustomerAddress;
import java.util.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

class CustomerProfileUpdaterTest {

    private final CustomerProfileUpdater updater = new CustomerProfileUpdater();

    @Test
    void applyCopiesEditableProfileFields() {
        Customer existing = new Customer();
        existing.setCustomerId(1);
        existing.setDeleted(false);

        CustomerAddress address = new CustomerAddress();
        Date birthdate = new Date();
        Customer updated = new Customer();
        updated.setFirstName("Jane");
        updated.setLastName("Meyer");
        updated.setEmail("jane@example.com");
        updated.setBirthdate(birthdate);
        updated.setFemale(false);
        updated.setCustomerAddress(address);

        updater.apply(existing, updated);

        assertEquals(1, existing.getCustomerId());
        assertEquals("Jane", existing.getFirstName());
        assertEquals("Meyer", existing.getLastName());
        assertEquals("jane@example.com", existing.getEmail());
        assertSame(birthdate, existing.getBirthdate());
        assertFalse(existing.isFemale());
        assertSame(address, existing.getCustomerAddress());
        assertFalse(existing.isDeleted());
    }
}
