package de.fherfurt.Vehicle;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.Year;

public class VehicleTest {

    private Vehicle vehicle;

    @Before
    public void setUp() {
        vehicle = new Vehicle(1,"Astra K","Opel",10000,2020,"Kombi");
    }

    @Test
    public void testCreateVehicle() {
        assertTrue(vehicle.CreateVehicle(1, "Car1", "Brand1", 10000, 2020, "Sedan"));
    }

    @Test
    public void testUpdateVehicle() {
        vehicle.CreateVehicle(1, "Car1", "Brand1", 10000, 2020, "Sedan");
        assertTrue(vehicle.UpdateVehicle(1, "CarUpdated", "BrandUpdated", 15000, 2022, "SUV"));
        assertEquals("CarUpdated", vehicle.getVehicleDetails(1).split("\n")[1].split(":")[1].trim());
    }

    @Test
    public void testDeleteVehicle() {
        vehicle.CreateVehicle(1, "Car1", "Brand1", 10000, 2020, "Sedan");
        assertTrue(vehicle.DeleteVehicle(1));
        assertFalse(vehicle.DeleteVehicle(999)); // Non-existing ID should return false
    }

    @Test
    public void testCheckNewKilometerCount() {
        vehicle.CreateVehicle(1, "Car1", "Brand1", 10000, 2020, "Sedan");
        assertTrue(vehicle.CheckNewKilometerCount(1, 12000));
        assertEquals(12000, vehicle.getVehicleDetails(1).split("\n")[3].split(":")[1].trim());
        assertFalse(vehicle.CheckNewKilometerCount(999, 13000)); // Non-existing ID should return false
    }

    @Test
    public void testGetVehicleDetails() {
        vehicle.CreateVehicle(1, "Car1", "Brand1", 10000, 2020, "Sedan");
        String details = vehicle.getVehicleDetails(1);
        assertNotNull(details);
        assertTrue(details.contains("Vehicle ID: 1"));
        assertTrue(details.contains("Name: Car1"));
        assertTrue(details.contains("Brand: Brand1"));
        assertTrue(details.contains("Kilometer Count: 10000"));
        assertTrue(details.contains("Construction Year: 2020"));
        assertTrue(details.contains("Type: Sedan"));
    }
}
