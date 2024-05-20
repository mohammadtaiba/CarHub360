package de.fherfurt.logic;

import static org.junit.Assert.*;

import de.fherfurt.logic.SaleVehicle;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class SaleVehicleTest {

    private SaleVehicle saleVehicle;

    @Before
    public void setUp() {
        saleVehicle = new SaleVehicle(1, BigDecimal.valueOf(30000.0), true);
    }

    @Test
    public void testGetVehicleId() {
        assertEquals(1, saleVehicle.getVehicleId());
    }

    @Test
    public void testSetVehicleId() {
        saleVehicle.setVehicleId(2);
        assertEquals(2, saleVehicle.getVehicleId());
    }

    @Test
    public void testGetSalePrice() {
        assertEquals(BigDecimal.valueOf(30000.0), saleVehicle.getSalePrice());
    }

    @Test
    public void testSetSalePrice() {
        saleVehicle.setSalePrice(BigDecimal.valueOf(35000.0));
        assertEquals(BigDecimal.valueOf(35000.0), saleVehicle.getSalePrice());
    }

    @Test
    public void testIsNew() {
        assertTrue(saleVehicle.isNew());
    }

    @Test
    public void testSetNew() {
        saleVehicle.setNew(false);
        assertFalse(saleVehicle.isNew());
    }

    @Test
    public void testGetDetails() {
        // Test retrieving the details of a sale vehicle
        String details = saleVehicle.getDetails();
        assertNotNull(details);
        assertTrue(details.contains("Vehicle ID: 1"));
        assertTrue(details.contains("Sale Price: 30000.0"));
        assertTrue(details.contains("Is New: true"));
    }
}
