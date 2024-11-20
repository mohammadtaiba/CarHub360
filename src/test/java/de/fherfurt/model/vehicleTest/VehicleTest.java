package de.fherfurt.model.vehicleTest;

import static org.junit.Assert.*;

import de.fherfurt.model.vehicle.Vehicle;
import de.fherfurt.model.vehicle.VehicleManager;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Vehicle and VehicleManager class.
 */
public class VehicleTest {
    private VehicleManager vehicleManager;
    private Vehicle vehicle;

    /**
     * Sets up the test environment before each test.
     */
    @Before
    public void setUp() {
        vehicleManager = new VehicleManager();
        vehicle = new Vehicle(1, "Yaris", "Toyota", 15000, 2010, "Small car");
        vehicleManager.createVehicle(1, "Yaris", "Toyota", 15000, 2010, "Small car");
    }

    /**
     * Tests the creation of vehicles.
     */
    @Test
    public void testCreateVehicle() {
        assertTrue(vehicleManager.createVehicle(2, "Cayenne", "Porsche", 10000, 2010, "Sports car"));
        assertFalse(vehicleManager.createVehicle(-1, "Sedan", "Toyota", 20000, 2024, "Small car"));
        assertFalse(vehicleManager.createVehicle(3, "Model S", "Tesla", 5000, 2025, "Electric car"));
    }

    /**
     * Tests updating existing vehicles.
     */
    @Test
    public void testUpdateVehicle() {
        assertTrue(vehicleManager.updateVehicle(1, "Yaris", "Toyota", 25000, 2010, "Small car"));
        assertFalse(vehicleManager.updateVehicle(4, "Civic", "Honda", 20000, 2015, "Sedan"));
    }

    /**
     * Tests deleting vehicles.
     */
    @Test
    public void testDeleteVehicle() {
        assertTrue(vehicleManager.deleteVehicle(1));
        assertFalse(vehicleManager.deleteVehicle(10));
    }

    /**
     * Tests checking and updating the kilometer count of vehicles.
     */
    @Test
    public void testCheckNewKilometerCount() {
        assertTrue(vehicleManager.checkNewKilometerCount(1, 20000));
        assertFalse(vehicleManager.checkNewKilometerCount(2, 20000));
        assertFalse(vehicleManager.checkNewKilometerCount(1, 5000));
    }

    /**
     * Tests retrieving vehicle details.
     */
    @Test
    public void testGetVehicleDetails() {
        String expected = "Vehicle Details: \n" +
                "Vehicle ID: 1\n" +
                "Name: Yaris\n" +
                "Brand: Toyota\n" +
                "Kilometer Count: 15000\n" +
                "Construction Year: 2010\n" +
                "Type: Small car";
        assertEquals(expected, vehicleManager.getVehicleDetails(1));

        assertEquals("Vehicle with ID 2 was not found.", vehicleManager.getVehicleDetails(2));
    }

    /**
     * Tests the getter and setter methods of the Vehicle class.
     */
    @Test
    public void testGettersAndSetters() {
        assertEquals(1, vehicle.getVehicleId());
        assertEquals("Yaris", vehicle.getName());
        assertEquals("Toyota", vehicle.getBrand());
        assertEquals(15000, vehicle.getKilometerCount());
        assertEquals(2010, vehicle.getConstructionYear());
        assertEquals("Small car", vehicle.getType());

        vehicle.setVehicleId(2);
        vehicle.setName("Cayenne");
        vehicle.setBrand("Porsche");
        vehicle.setKilometerCount(10000);
        vehicle.setConstructionYear(2015);
        vehicle.setType("Sports car");

        assertEquals(2, vehicle.getVehicleId());
        assertEquals("Cayenne", vehicle.getName());
        assertEquals("Porsche", vehicle.getBrand());
        assertEquals(10000, vehicle.getKilometerCount());
        assertEquals(2015, vehicle.getConstructionYear());
        assertEquals("Sports car", vehicle.getType());
    }
}
