package de.fherfurt.logic;

import de.fherfurt.core.Customer;
import de.fherfurt.logic.customerHistory.CustomerHistory;
import de.fherfurt.logic.customerHistory.CustomerHistoryReview;
import de.fherfurt.model.Vehicle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class CustomerHistoryTest {

    private CustomerHistory customerHistory;
    private Customer customer;
    private Vehicle vehicle;
    private CustomerHistoryReview review;

    @Before
    public void setUp() {
        // Initializing dummy objects for testing
        customer = new Customer(1, "John", "Doe", "john.doe@example.com", new Date(), true, null);
        vehicle = new Vehicle(1, "CarModel", "CarBrand", 1000, 2020, "Sedan");
        review = CustomerHistoryReview.FÜNF;

        customerHistory = new CustomerHistory(1, customer, vehicle, review, "Test Description", new Date(), true);
        customerHistory.createCustomerHistory(1, customer, vehicle, review, "Test Description", new Date(), true);
    }

    @After
    public void tearDown() {
        customerHistory = null;
        customer = null;
        vehicle = null;
        review = null;
    }

    @Test
    public void testCreateCustomerHistory() {
        assertTrue(customerHistory.createCustomerHistory(2, customer, vehicle, CustomerHistoryReview.VIER, "New Description", new Date(), false));
        assertTrue(customerHistory.createCustomerHistory(2, customer, vehicle, CustomerHistoryReview.VIER, "New Description", new Date(), false));
        assertFalse(customerHistory.createCustomerHistory(-1, customer, vehicle, CustomerHistoryReview.DREI, "Invalid Description", new Date(), false));
        assertFalse(customerHistory.createCustomerHistory(3, null, vehicle, CustomerHistoryReview.ZWEI, "Invalid Description", new Date(), false));
        assertFalse(customerHistory.createCustomerHistory(4, customer, null, CustomerHistoryReview.EINS, "Invalid Description", new Date(), false));
        assertFalse(customerHistory.createCustomerHistory(5, customer, vehicle, null, "Invalid Description", new Date(), false));
        assertFalse(customerHistory.createCustomerHistory(6, customer, vehicle, CustomerHistoryReview.DREI, null, new Date(), false));
        assertFalse(customerHistory.createCustomerHistory(7, customer, vehicle, CustomerHistoryReview.ZWEI, "Invalid Description", null, false));
    }

    @Test
    public void testGetCustomerHistory() {
        CustomerHistory history = customerHistory.getCustomerHistory(1);
        assertNotNull(history);
        assertEquals(1, history.getCustomerHistoryId());
        assertEquals("Test Description", history.getDescription());

        CustomerHistory invalidHistory = customerHistory.getCustomerHistory(999);
        assertNull(invalidHistory);
    }

    @Test
    public void testGetCustomerFinalReview() {
        CustomerHistoryReview finalReview = customerHistory.getCustomerFinalReview(1);
        assertNotNull(finalReview);
        assertEquals(CustomerHistoryReview.FÜNF, finalReview);

        CustomerHistoryReview invalidReview = customerHistory.getCustomerFinalReview(999);
        assertNull(invalidReview);
    }

    @Test
    public void testGettersAndSetters() {
        // Testing getter methods
        assertEquals(1, customerHistory.getCustomerHistoryId());
        assertEquals(customer, customerHistory.getCustomer());
        assertEquals(vehicle, customerHistory.getCustomerHistoryVehicle());
        assertEquals(review, customerHistory.getCustomerHistoryReview());
        assertEquals("Test Description", customerHistory.getDescription());
        assertEquals(true, customerHistory.isForRentalCar());
        // You can add more assertions for other getters

        // Testing setter methods
        Customer newCustomer = new Customer(2, "Jane", "Doe", "jane.doe@example.com", new Date(), true, null);
        Vehicle newVehicle = new Vehicle(2, "CarModel2", "CarBrand2", 2000, 2022, "SUV");
        CustomerHistoryReview newReview = CustomerHistoryReview.VIER;
        Date newDate = new Date();
        customerHistory.setCustomerHistoryId(2);
        customerHistory.setCustomer(newCustomer);
        customerHistory.setCustomerHistoryVehicle(newVehicle);
        customerHistory.setCustomerHistoryReview(newReview);
        customerHistory.setDescription("New Description");
        customerHistory.setActionDate(newDate);
        customerHistory.setForRentalCar(false);

        assertEquals(2, customerHistory.getCustomerHistoryId());
        assertEquals(newCustomer, customerHistory.getCustomer());
        assertEquals(newVehicle, customerHistory.getCustomerHistoryVehicle());
        assertEquals(newReview, customerHistory.getCustomerHistoryReview());
        assertEquals("New Description", customerHistory.getDescription());
        assertEquals(newDate, customerHistory.getActionDate());
        assertEquals(false, customerHistory.isForRentalCar());
        // You can add more assertions for other setters
    }
}
