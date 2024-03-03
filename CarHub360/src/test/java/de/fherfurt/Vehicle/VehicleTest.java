package de.fherfurt.Vehicle;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
public class VehicleTest {
    private Vehicle vehicle;

    @Before
    public void setUp() {
        vehicle = new Vehicle(1, "Yaris", "Toyota", 15000, 2024, "Small car");
    }

    @Test
    public void testCreateVehicle() {
        assertTrue(vehicle.CreateVehicle(1, "Yaris", "Toyota", 15000, 2010, "Small car"));
        assertFalse(vehicle.CreateVehicle(-1, "Cayenne", "Porsche", 10000, 2024, "Sports car"));
        assertFalse(vehicle.CreateVehicle(2, "Sedan", "Toyota", 20000, 2025, "Small car"));
    }

    @Test
    public void testUpdateVehicle() {
        vehicle.CreateVehicle(1, "Yaris", "Toyota", 15000, 2010, "Small car");
        assertTrue(vehicle.UpdateVehicle(1, "Yaris", "Toyota", 25000, 2010, "Small car"));
        assertFalse(vehicle.UpdateVehicle(2, "Yaris", "Toyota", 25000, 2010, "Small car"));
    }

    @Test
    public void testDeleteVehicle() {
        vehicle.CreateVehicle(1, "Yaris", "Toyota", 15000, 2010, "Small car");
        assertTrue(vehicle.DeleteVehicle(1));
        assertFalse(vehicle.DeleteVehicle(2));
    }

    @Test
    public void testCheckNewKilometerCount() {
        vehicle.CreateVehicle(1, "Yaris", "Toyota", 15000, 2010, "Small car");
        assertTrue(vehicle.checkNewKilometerCount(1, 20000));
        assertFalse(vehicle.checkNewKilometerCount(2, 20000));
        assertFalse(vehicle.checkNewKilometerCount(1, 5000));
    }

    @Test
    public void testGetVehicleDetails() {
        vehicle.CreateVehicle(1, "Yaris", "Toyota", 15000, 2010, "Small car");
        String expected = "Vehicle Details: \n" +
                "Vehicle ID: 1\n" +
                "Name: Yaris\n" +
                "Brand: Toyota\n" +
                "Kilometer Count: 15000\n" +
                "Construction Year: 2010\n" +
                "Type: Small car";
        assertEquals(expected, vehicle.getVehicleDetails(1));

        assertEquals("Vehicle with ID 2 was not found.", vehicle.getVehicleDetails(2));
    }
}