package de.fherfurt.Vehicle;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class VehicleTest {
    private Vehicle vehicle;

    @Before
    public void setUp() {
        // Initialize the Vehicle object and add it to the list
        vehicle = new Vehicle(1, "Yaris", "Toyota", 15000, 2010, "Small car");
        Vehicle.createVehicle(1, "Yaris", "Toyota", 15000, 2010, "Small car");
    }

    @Test
    public void testCreateVehicle() {
        // Valid vehicle creation
        assertTrue(Vehicle.createVehicle(2, "Cayenne", "Porsche", 10000, 2010, "Sports car"));
        // Invalid vehicle creation (negative ID)
        assertFalse(Vehicle.createVehicle(-1, "Sedan", "Toyota", 20000, 2024, "Small car"));
        // Invalid vehicle creation (future construction year)
        assertFalse(Vehicle.createVehicle(3, "Model S", "Tesla", 5000, 2025, "Electric car"));
    }

    @Test
    public void testUpdateVehicle() {
        // Vehicle update for an existing vehicle
        assertTrue(Vehicle.updateVehicle(1, "Yaris", "Toyota", 25000, 2010, "Small car"));
        // Vehicle update for a non-existing vehicle
        assertFalse(Vehicle.updateVehicle(4, "Civic", "Honda", 20000, 2015, "Sedan"));
    }

    @Test
    public void testDeleteVehicle() {
        // Deleting an existing vehicle
        assertTrue(Vehicle.deleteVehicle(1));
        // Deleting a non-existing vehicle
        assertFalse(Vehicle.deleteVehicle(2));
    }

    @Test
    public void testCheckNewKilometerCount() {
        // Valid kilometer update
        assertTrue(Vehicle.checkNewKilometerCount(1, 20000));
        // Invalid kilometer update (non-existing vehicle)
        assertFalse(Vehicle.checkNewKilometerCount(2, 20000));
        // Invalid kilometer update (lower kilometer count)
        assertFalse(Vehicle.checkNewKilometerCount(1, 5000));
    }

    @Test
    public void testGetVehicleDetails() {
        // Retrieve details of an existing vehicle
        String expected = "Vehicle Details: \n" +
                "Vehicle ID: 1\n" +
                "Name: Yaris\n" +
                "Brand: Toyota\n" +
                "Kilometer Count: 15000\n" +
                "Construction Year: 2010\n" +
                "Type: Small car";
        assertEquals(expected, Vehicle.getVehicleDetails(1));

        // Retrieve details of a non-existing vehicle
        assertEquals("Vehicle with ID 2 was not found.", Vehicle.getVehicleDetails(2));
    }
}
