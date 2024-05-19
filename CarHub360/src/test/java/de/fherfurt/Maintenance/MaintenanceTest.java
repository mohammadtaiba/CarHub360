package de.fherfurt.Maintenance;

import static org.junit.Assert.*;

import de.fherfurt.Vehicle.Vehicle;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

public class MaintenanceTest {

    private Maintenance maintenance;

    @Before
    public void setUp() {
        // Initialize the maintenance object before each test
        maintenance = new Maintenance();
    }

    @Test
    public void testAddMaintenance() {
        // Valid maintenance entry
        Vehicle vehicle = new Vehicle(1, "Astra K", "Opel", 10000, 2020, "Kombi");
        Date startDate = new Date();
        Date endDate = new Date();
        float cost = 100.0f;
        String description = "Routine maintenance";

        assertTrue(maintenance.addMaintenance(1, vehicle, startDate, endDate, cost, description));
    }

    @Test
    public void testGetMaintenanceDetails() {
        // Valid maintenance details retrieval
        Vehicle vehicle = new Vehicle(1, "Astra K", "Opel", 10000, 2020, "Kombi");
        Date startDate = new Date();
        Date endDate = new Date();
        float cost = 100.0f;
        String description = "Routine maintenance";

        maintenance.addMaintenance(1, vehicle, startDate, endDate, cost, description);

        String details = maintenance.getMaintenanceDetails(1);
        assertNotNull(details);
        assertTrue(details.contains("Maintenance ID: 1"));
        assertTrue(details.contains("Vehicle: " + vehicle));
        assertTrue(details.contains("Maintenance Start Date: " + startDate));
        assertTrue(details.contains("Maintenance End Date: " + endDate));
        assertTrue(details.contains("Maintenance Cost: " + cost));
        assertTrue(details.contains("Maintenance Description: " + description));
    }

    @Test
    public void testGetMaintenanceDetailsNotFound() {
        // Maintenance details retrieval for non-existing maintenance
        String details = maintenance.getMaintenanceDetails(999);
        assertNotNull(details);
        assertTrue(details.contains("Maintenance with ID 999 was not found."));
    }
}
