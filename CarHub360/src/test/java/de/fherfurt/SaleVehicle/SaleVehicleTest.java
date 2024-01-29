package de.fherfurt.SaleVehicle;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SaleVehicleTest {

    private SaleVehicle saleVehicle;

    @Before
    public void setUp() {
        saleVehicle = new SaleVehicle(1, "Car1", "Brand1", 10000, 2020, "Sedan", 30000.0f, true);
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
        assertEquals(30000.0f, saleVehicle.getSalePrice(), 0.01);
    }

    @Test
    public void testSetSalePrice() {
        saleVehicle.setSalePrice(35000.0f);
        assertEquals(35000.0f, saleVehicle.getSalePrice(), 0.01);
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
    public void testGetSaleVehicleDetails() {
        String details = saleVehicle.getSaleVehicleDetails();
        assertNotNull(details);
        assertTrue(details.contains("Vehicle ID: 1"));
        assertTrue(details.contains("Sale Price: 30000.0"));
        assertTrue(details.contains("Is New: true"));
    }
}
