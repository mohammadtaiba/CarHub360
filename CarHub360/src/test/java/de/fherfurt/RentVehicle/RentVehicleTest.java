package de.fherfurt.RentVehicle;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class RentVehicleTest {

    private RentVehicle rentVehicle;

    @Before
    public void setUp() {
        rentVehicle = new RentVehicle(1, true, BigDecimal.valueOf(100.0), "AB-1234", BigDecimal.valueOf(500.0));
    }

    @Test
    public void testGetRentVehicleId() {
        assertEquals(1, rentVehicle.getRentVehicleId());
    }

    @Test
    public void testSetRentVehicleId() {
        rentVehicle.setRentVehicleId(2);
        assertEquals(2, rentVehicle.getRentVehicleId());
    }

    @Test
    public void testIsAvailable() {
        assertTrue(rentVehicle.isAvailable());
    }

    @Test
    public void testSetAvailable() {
        rentVehicle.setAvailable(false);
        assertFalse(rentVehicle.isAvailable());
    }

    @Test
    public void testGetDailyPrice() {
        assertEquals(BigDecimal.valueOf(100.0), rentVehicle.getDailyPrice());
    }

    @Test
    public void testSetDailyPrice() {
        rentVehicle.setDailyPrice(BigDecimal.valueOf(150.0));
        assertEquals(BigDecimal.valueOf(150.0), rentVehicle.getDailyPrice());
    }

    @Test
    public void testGetLicensePlate() {
        assertEquals("AB-1234", rentVehicle.getLicensePlate());
    }

    @Test
    public void testSetLicensePlate() {
        rentVehicle.setLicensePlate("CD-5678");
        assertEquals("CD-5678", rentVehicle.getLicensePlate());
    }

    @Test
    public void testGetDeposit() {
        assertEquals(BigDecimal.valueOf(500.0), rentVehicle.getDeposit());
    }

    @Test
    public void testSetDeposit() {
        rentVehicle.setDeposit(BigDecimal.valueOf(600.0));
        assertEquals(BigDecimal.valueOf(600.0), rentVehicle.getDeposit());
    }

    @Test
    public void testGetDetails() {
        // Test retrieving the details of a rent vehicle
        String details = rentVehicle.getDetails();
        assertNotNull(details);
        assertTrue(details.contains("Rent Vehicle ID: 1"));
        assertTrue(details.contains("Daily Price: 100.0"));
        assertTrue(details.contains("License Plate: AB-1234"));
    }
}
