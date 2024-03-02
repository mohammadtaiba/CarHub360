package de.fherfurt.customerHistory;
import de.fherfurt.customer.Customer;
import de.fherfurt.customerHistory.CustomerHistory;
import de.fherfurt.customerHistory.CustomerHistoryReview;
import de.fherfurt.Vehicle.Vehicle;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
public class customerHistory {

    @Test
    public void createCustomerHistory_ValidInput_Success() {
        Customer customer = new Customer();
        Vehicle vehicle = new Vehicle(1, "TestVehicle", "TestBrand", 10000, 2020, "Car");
        Date date = new Date();
        CustomerHistory customerHistory = new CustomerHistory();

        assertTrue(customerHistory.createCustomerHistory(1, customer, vehicle, CustomerHistoryReview.EINS, "Description", date, true));
    }

    @Test
    public void createCustomerHistory_InvalidInput_Failure() {
        CustomerHistory customerHistory = new CustomerHistory();

        assertFalse(customerHistory.createCustomerHistory(-1, null, null, null, null, null, false));
    }

    @Test
    public void getCustomerHistory_ValidId_Success() {
        Customer customer = new Customer();
        Vehicle vehicle = new Vehicle(1, "TestVehicle", "TestBrand", 10000, 2020, "Car");
        Date date = new Date();
        CustomerHistory customerHistory = new CustomerHistory();
        customerHistory.createCustomerHistory(1, customer, vehicle, CustomerHistoryReview.EINS, "Description", date, true);

        assertNotNull(customerHistory.getCustomerHistory(1));
    }

    @Test
    public void getCustomerHistory_InvalidId_ReturnsNull() {
        CustomerHistory customerHistory = new CustomerHistory();

        assertNull(customerHistory.getCustomerHistory(100));
    }

    @Test
    public void getCustomerFinalReview_ValidId_Success() {
        Customer customer = new Customer();
        Vehicle vehicle = new Vehicle(1, "TestVehicle", "TestBrand", 10000, 2020, "Car");
        Date date = new Date();
        CustomerHistory customerHistory = new CustomerHistory();
        customerHistory.createCustomerHistory(1, customer, vehicle, CustomerHistoryReview.EINS, "Description", date, true);

        assertNotNull(customerHistory.getCustomerFinalReview(1));
    }

    @Test
    public void getCustomerFinalReview_InvalidId_ReturnsNull() {
        CustomerHistory customerHistory = new CustomerHistory();

        assertNull(customerHistory.getCustomerFinalReview(100));
    }
}
