package de.fherfurt.RentVehicle;



import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RentVehicleTest {

    private RentVehicle rentVehicle;

    @Before
    public void setUp() {
        rentVehicle = new RentVehicle(1, "Car1", "Brand1", 10000, 2020, "Sedan",
                101, 1, true, 50.0, "ABC123", 200.0f);
    }

    @Test
    public void testGetRentVehicleId() {
        assertEquals(101, rentVehicle.getRentVehicleId());
    }

    @Test
    public void testSetRentVehicleId() {
        rentVehicle.setRentVehicleId(102);
        assertEquals(102, rentVehicle.getRentVehicleId());
    }

    @Test
    public void testGetRentVehicleVehicleId() {
        assertEquals(1, rentVehicle.getRentVehicleVehicleId());
    }

    @Test
    public void testSetRentVehicleVehicleId() {
        rentVehicle.setRentVehicleVehicleId(2);
        assertEquals(2, rentVehicle.getRentVehicleVehicleId());
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
        assertEquals(50.0, rentVehicle.getDailyPrice(), 0.01);
    }

    @Test
    public void testSetDailyPrice() {
        rentVehicle.setDailyPrice(60.0);
        assertEquals(60.0, rentVehicle.getDailyPrice(), 0.01);
    }

    @Test
    public void testGetLicensePlate() {
        assertEquals("ABC123", rentVehicle.getLicensePlate());
    }

    @Test
    public void testSetLicensePlate() {
        rentVehicle.setLicensePlate("XYZ789");
        assertEquals("XYZ789", rentVehicle.getLicensePlate());
    }

    @Test
    public void testGetDeposit() {
        assertEquals(200.0f, rentVehicle.getDeposit(), 0.01);
    }

    @Test
    public void testSetDeposit() {
        rentVehicle.setDeposit(250.0f);
        assertEquals(250.0f, rentVehicle.getDeposit(), 0.01);
    }

    @Test
    public void testGetRentVehicleDetails() {
        String details = rentVehicle.getRentVehicleDetails();
        assertNotNull(details);
        assertTrue(details.contains("Rent Vehicle ID: 101"));
        assertTrue(details.contains("Is Available: true"));
        assertTrue(details.contains("Daily Price: 50.0"));
        assertTrue(details.contains("License Plate: ABC123"));
        assertTrue(details.contains("Deposit: 200.0"));
    }
}

