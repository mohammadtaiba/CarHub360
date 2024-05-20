package de.fherfurt.core;

import de.fherfurt.core.Contract;
import de.fherfurt.logic.RentVehicle;
import de.fherfurt.logic.SaleVehicle;
import de.fherfurt.core.Customer;
import de.fherfurt.logic.CustomerAddress;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class ContractTest {

    private Customer mockCustomer;
    private RentVehicle mockRentVehicle;
    private SaleVehicle mockSaleVehicle;
    private LocalDate mockStartDate;
    private LocalDate mockEndDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Before
    public void setUp() throws Exception {

        CustomerAddress mockAddress = new CustomerAddress(1, "Anytown", "123", "12345", "Country");


        mockCustomer = new Customer(1, "Mohammad", "Taiba", "mohammadtaiba55@gmail.com",
                sdf.parse("01/01/1999"), false, mockAddress);


        mockRentVehicle = new RentVehicle(1, true, new BigDecimal("500.00"), "BMW", new BigDecimal("5"));


        mockSaleVehicle = new SaleVehicle(2, new BigDecimal("30000.00"), true);

        mockStartDate = LocalDate.now().plusDays(1); // Start date in the future
        mockEndDate = mockStartDate.plusDays(7); // End date one week later
    }

    @Test
    public void createPurchaseContract_Success() {
        assertTrue(Contract.createPurchaseContract(1, mockCustomer, mockSaleVehicle));
    }

    @Test
    public void createRentalContract_Success() {
        assertTrue(Contract.createRentalContract(2, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate));
    }

    @Test
    public void createRentalContract_Failure() {
        assertFalse(Contract.createRentalContract(2, mockCustomer, mockRentVehicle, mockEndDate, mockStartDate)); // End date before start date
        assertFalse(Contract.createRentalContract(-1, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate)); // Invalid contract ID
        assertFalse(Contract.createRentalContract(2, mockCustomer, null, mockStartDate, mockEndDate)); // Null rent vehicle
        assertFalse(Contract.createRentalContract(2, mockCustomer, mockRentVehicle, null, mockEndDate)); // Null start date
        assertFalse(Contract.createRentalContract(2, mockCustomer, mockRentVehicle, mockStartDate, null)); // Null end date
    }

    @Test
    public void terminateRentalContract_Success() {
        Contract.createRentalContract(3, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertTrue(Contract.terminateRentalContract(3));
    }

    @Test
    public void terminateRentalContract_Failure() {
        Contract.createRentalContract(3, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertFalse(Contract.terminateRentalContract(2)); // Non-existent contract ID
    }

    @Test
    public void renewRentalContract_Success() {
        Contract.createRentalContract(4, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertTrue(Contract.renewRentalContract(4, mockEndDate.plusDays(7)));
    }

    @Test
    public void renewRentalContract_Failure() {
        Contract.createRentalContract(4, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertFalse(Contract.renewRentalContract(4, mockEndDate.minusDays(8))); // New end date before start date
    }

    @Test
    public void getTotalPrice_Valid() {
        Contract.createRentalContract(5, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);

        BigDecimal totalPrice = Contract.getTotalPrice(5);
        BigDecimal expectedPrice = BigDecimal.valueOf(3500.0);
        assertTrue(totalPrice.compareTo(expectedPrice) == 0);
    }


    @Test
    public void getRentalContractDetails_Valid() {
        Contract.createRentalContract(6, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        String details = Contract.getRentalContractDetails(6);
        assertNotNull(details);
        assertTrue(details.contains("Contract ID: 6"));
    }

    @Test
    public void validateRentalPeriod_Valid() throws Exception {
        Method method = Contract.class.getDeclaredMethod("validateRentalPeriod", LocalDate.class, LocalDate.class);
        method.setAccessible(true);
        assertTrue((Boolean) method.invoke(null, mockStartDate, mockEndDate));
    }

    @Test
    public void validateRentalPeriod_Invalid() throws Exception {
        Method method = Contract.class.getDeclaredMethod("validateRentalPeriod", LocalDate.class, LocalDate.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(null, mockEndDate, mockStartDate));
    }
}
