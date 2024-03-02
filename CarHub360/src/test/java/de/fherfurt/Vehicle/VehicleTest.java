package de.fherfurt.Vehicle;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
public class VehicleTest {
    private Vehicle vehicle;

    @Before
    public void setUp() {
        vehicle = new Vehicle(1, "Car", "Toyota", 10000, 2010, "Sedan");
    }

    @Test
    public void testCreateVehicle() {
        assertTrue(vehicle.CreateVehicle(1, "Car", "Toyota", 10000, 2010, "Sedan"));
        assertFalse(vehicle.CreateVehicle(-1, null, "Toyota", 10000, 2010, "Sedan"));
        assertFalse(vehicle.CreateVehicle(2, "Car", "Toyota", 10000, 2025, "Sedan"));
    }

    @Test
    public void testUpdateVehicle() {
        vehicle.CreateVehicle(1, "Car", "Toyota", 10000, 2010, "Sedan");
        assertTrue(vehicle.UpdateVehicle(1, "Updated Car", "Updated Brand", 20000, 2015, "SUV"));
        assertFalse(vehicle.UpdateVehicle(2, "Updated Car", "Updated Brand", 20000, 2015, "SUV"));
    }

    @Test
    public void testDeleteVehicle() {
        vehicle.CreateVehicle(1, "Car", "Toyota", 10000, 2010, "Sedan");
        assertTrue(vehicle.DeleteVehicle(1));
        assertFalse(vehicle.DeleteVehicle(2));
    }

    @Test
    public void testCheckNewKilometerCount() {
        vehicle.CreateVehicle(1, "Car", "Toyota", 10000, 2010, "Sedan");
        assertTrue(vehicle.checkNewKilometerCount(1, 20000));
        assertFalse(vehicle.checkNewKilometerCount(2, 20000));
        assertFalse(vehicle.checkNewKilometerCount(1, 5000));
    }

    @Test
    public void testGetVehicleDetails() {
        vehicle.CreateVehicle(1, "Car", "Toyota", 10000, 2010, "Sedan");
        String expected = "Vehicle Details: \n" +
                "Vehicle ID: 1\n" +
                "Name: Car\n" +
                "Brand: Toyota\n" +
                "Kilometer Count: 10000\n" +
                "Construction Year: 2010\n" +
                "Type: Sedan";
        assertEquals(expected, vehicle.getVehicleDetails(1));

        assertEquals("Vehicle with ID 2 was not found.", vehicle.getVehicleDetails(2));
    }
}