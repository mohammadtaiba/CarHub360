package de.fherfurt.Vehicle;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class VehicleTest {
    private Vehicle vehicle;

    @Before
    public void setUp() {
        // Initialisieren Sie das Fahrzeug-Objekt und fügen Sie es der Liste hinzu
        vehicle = new Vehicle(1, "Yaris", "Toyota", 15000, 2010, "Small car");
        Vehicle.createVehicle(1, "Yaris", "Toyota", 15000, 2010, "Small car");
    }

    @Test
    public void testCreateVehicle() {
        // Gültige Fahrzeugerstellung
        assertTrue(Vehicle.createVehicle(2, "Cayenne", "Porsche", 10000, 2010, "Sports car"));
        // Ungültige Fahrzeugerstellung (negative ID)
        assertFalse(Vehicle.createVehicle(-1, "Sedan", "Toyota", 20000, 2024, "Small car"));
        // Ungültige Fahrzeugerstellung (künftiges Baujahr)
        assertFalse(Vehicle.createVehicle(3, "Model S", "Tesla", 5000, 2025, "Electric car"));
    }

    @Test
    public void testUpdateVehicle() {
        // Fahrzeug-Update für ein bestehendes Fahrzeug
        assertTrue(Vehicle.updateVehicle(1, "Yaris", "Toyota", 25000, 2010, "Small car"));
        // Fahrzeug-Update für ein nicht existierendes Fahrzeug
        assertFalse(Vehicle.updateVehicle(4, "Civic", "Honda", 20000, 2015, "Sedan"));
    }

    @Test
    public void testDeleteVehicle() {
        // Löschen eines bestehenden Fahrzeugs
        assertTrue(Vehicle.deleteVehicle(1));
        // Löschen eines nicht existierenden Fahrzeugs
        assertFalse(Vehicle.deleteVehicle(2));
    }

    @Test
    public void testCheckNewKilometerCount() {
        // Gültiges Kilometer-Update
        assertTrue(Vehicle.checkNewKilometerCount(1, 20000));
        // Ungültiges Kilometer-Update (nicht existierendes Fahrzeug)
        assertFalse(Vehicle.checkNewKilometerCount(2, 20000));
        // Ungültiges Kilometer-Update (niedrigere Kilometerzahl)
        assertFalse(Vehicle.checkNewKilometerCount(1, 5000));
    }

    @Test
    public void testGetVehicleDetails() {
        // Details eines existierenden Fahrzeugs abrufen
        String expected = "Vehicle Details: \n" +
                "Vehicle ID: 1\n" +
                "Name: Yaris\n" +
                "Brand: Toyota\n" +
                "Kilometer Count: 15000\n" +
                "Construction Year: 2010\n" +
                "Type: Small car";
        assertEquals(expected, Vehicle.getVehicleDetails(1));

        // Details eines nicht existierenden Fahrzeugs abrufen
        assertEquals("Vehicle with ID 2 was not found.", Vehicle.getVehicleDetails(2));
    }
}
