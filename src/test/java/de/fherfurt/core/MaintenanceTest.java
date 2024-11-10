package de.fherfurt.core;

import static org.junit.Assert.*;

import de.fherfurt.model.Vehicle;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class MaintenanceTest {

    private Maintenance maintenance;

    @Before
    public void setUp() {
        maintenance = new Maintenance();
    }

    @Test
    public void testAddMaintenance() {
        Vehicle vehicle = new Vehicle(1, "Astra K", "Opel", 10000, 2020, "Kombi");
        Date startDate = new Date();
        Date endDate = new Date();
        float cost = 100.0f;
        String description = "Routine maintenance";

        assertTrue(maintenance.addMaintenance(1, vehicle, startDate, endDate, cost, description));
    }

    @Test
    public void testAddMaintenanceInvalidVehicle() {
        Date startDate = new Date();
        Date endDate = new Date();
        float cost = 100.0f;
        String description = "Routine maintenance";

        assertFalse(maintenance.addMaintenance(2, null, startDate, endDate, cost, description));
    }

    @Test
    public void testGetMaintenanceDetails() {
        Vehicle vehicle = new Vehicle(1, "Astra K", "Opel", 10000, 2020, "Kombi");
        Date startDate = new Date();
        Date endDate = new Date();
        float cost = 100.0f;
        String description = "Routine maintenance";

        maintenance.addMaintenance(1, vehicle, startDate, endDate, cost, description);

        String details = maintenance.getMaintenanceDetails(1);
        assertNotNull(details);
        assertTrue(details.contains("Maintenance ID: 1"));
        assertTrue(details.contains("Vehicle: " + vehicle.toString()));
        assertTrue(details.contains("Maintenance Start Date: " + startDate));
        assertTrue(details.contains("Maintenance End Date: " + endDate));
        assertTrue(details.contains("Maintenance Cost: " + cost));
        assertTrue(details.contains("Maintenance Description: " + description));
    }

    @Test
    public void testGetMaintenanceDetailsNotFound() {
        String details = maintenance.getMaintenanceDetails(999);
        assertNotNull(details);
        assertTrue(details.contains("Maintenance with ID 999 was not found."));
    }

    @Test
    public void testDefaultConstructor() {
        Maintenance m = new Maintenance();
        m.setMaintenanceId(1);
        Vehicle vehicle = new Vehicle(2, "Model S", "Tesla", 50000, 2021, "Sedan");
        m.setVehicle(vehicle);
        Date startDate = new Date();
        Date endDate = new Date();
        m.setMaintenanceStartDate(startDate);
        m.setMaintenanceEndDate(endDate);
        m.setMaintenanceCost(200.0f);
        m.setMaintenanceDescription("Battery replacement");

        assertEquals(1, m.getMaintenanceId());
        assertEquals(vehicle, m.getVehicle());
        assertEquals(startDate, m.getMaintenanceStartDate());
        assertEquals(endDate, m.getMaintenanceEndDate());
        assertEquals(200.0f, m.getMaintenanceCost(), 0);
        assertEquals("Battery replacement", m.getMaintenanceDescription());
    }

    @Test
    public void testParameterizedConstructor() {
        Vehicle vehicle = new Vehicle(3, "Civic", "Honda", 15000, 2018, "Sedan");
        Date startDate = new Date();
        Date endDate = new Date();
        Maintenance m = new Maintenance(2, vehicle, startDate, endDate, 150.0f, "Brake pad replacement");

        assertEquals(2, m.getMaintenanceId());
        assertEquals(vehicle, m.getVehicle());
        assertEquals(startDate, m.getMaintenanceStartDate());
        assertEquals(endDate, m.getMaintenanceEndDate());
        assertEquals(150.0f, m.getMaintenanceCost(), 0);
        assertEquals("Brake pad replacement", m.getMaintenanceDescription());
    }
}
