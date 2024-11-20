package de.fherfurt.core.contract;


import de.fherfurt.logic.rentVehicle.RentVehicle;
import de.fherfurt.logic.saleVehicle.SaleVehicle;

import de.fherfurt.core.customer.Customer;
import de.fherfurt.core.customer.CustomerManager;

import de.fherfurt.logic.customerAddress.CustomerAddress;
import de.fherfurt.logic.customerAddress.CustomerAddressManager;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Test class for Contract functionalities.
 */
public class ContractTest {

    private Contract mockContract;
    private ContractManager mockContractManager;

    private Customer mockCustomer;
    private CustomerManager mockCustomerManager;

    private CustomerAddressManager mockCustomerAddressManager;

    private RentVehicle mockRentVehicle;
    private SaleVehicle mockSaleVehicle;

    private LocalDate mockStartDate;
    private LocalDate mockEndDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Before
    public void setUp() throws Exception {
        CustomerAddress mockAddress = new CustomerAddress(123, "Erfurt", "99084", "Main Street", "123");
        mockCustomer = new Customer(1, "John", "Doe", "john.doe@example.com",
                sdf.parse("02/02/1990"), false, mockAddress);
        mockRentVehicle = new RentVehicle(1, true, new BigDecimal("500.00"), "BMW", new BigDecimal("5"));
        mockSaleVehicle = new SaleVehicle(2, new BigDecimal("30000.00"), true);

        mockStartDate = LocalDate.now().plusDays(1);
        mockEndDate = mockStartDate.plusDays(7);

        mockContractManager = new ContractManager();
    }

    @Test
    public void createPurchaseContract_Success() {
        assertTrue(mockContractManager.createPurchaseContract(1, mockCustomer, mockSaleVehicle));
    }

    @Test
    public void createRentalContract_Success() {
        assertTrue(mockContractManager.createRentalContract(2, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate));
    }

    @Test
    public void createRentalContract_Failure() {
        assertFalse(mockContractManager.createRentalContract(2, mockCustomer, mockRentVehicle, mockEndDate, mockStartDate));
        assertFalse(mockContractManager.createRentalContract(-1, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate));
        assertFalse(mockContractManager.createRentalContract(2, mockCustomer, null, mockStartDate, mockEndDate));
        assertFalse(mockContractManager.createRentalContract(2, mockCustomer, mockRentVehicle, null, mockEndDate));
        assertFalse(mockContractManager.createRentalContract(2, mockCustomer, mockRentVehicle, mockStartDate, null));
    }

    @Test
    public void terminateRentalContract_Success() {
        mockContractManager.createRentalContract(3, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertTrue(mockContractManager.terminateRentalContract(3));
    }

    @Test
    public void terminateRentalContract_Failure() {
        mockContractManager.createRentalContract(3, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertFalse(mockContractManager.terminateRentalContract(2));
    }

    @Test
    public void renewRentalContract_Success() {
        mockContractManager.createRentalContract(4, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertTrue(mockContractManager.renewRentalContract(4, mockEndDate.plusDays(7)));
    }

    @Test
    public void renewRentalContract_Failure() {
        mockContractManager.createRentalContract(4, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertFalse(mockContractManager.renewRentalContract(4, mockEndDate.minusDays(8)));
    }

    @Test
    public void getTotalPrice_Valid() {
        mockContractManager.createRentalContract(5, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);

        BigDecimal totalPrice = mockContractManager.getTotalPrice(5);
        BigDecimal expectedPrice = BigDecimal.valueOf(3500.0);
        assertTrue(totalPrice.compareTo(expectedPrice) == 0);

        BigDecimal totalPrice2 = mockContractManager.getTotalPrice(90);
        BigDecimal expectedPrice2 = BigDecimal.valueOf(-1);
        assertTrue(totalPrice2.compareTo(expectedPrice2) == 0);
    }

    @Test
    public void getRentalContractDetails_Valid() {
        mockContractManager.createRentalContract(6, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        String details = mockContractManager.getRentalContractDetails(6);
        assertNotNull(details);
        assertTrue(details.contains("Contract ID: 6"));
        String details2 = mockContractManager.getRentalContractDetails(100);
        assertTrue(details2.contains("No rental contract found with this ID."));
    }

    @Test
    public void getPurchaseContractDetails_Valid() {
        mockContractManager.createPurchaseContract(7, mockCustomer, mockSaleVehicle);
        String details = mockContractManager.getPurchaseContractDetails(7);
        assertNotNull(details);
        assertTrue(details.contains("Contract ID: 7"));
        String details2 = mockContractManager.getPurchaseContractDetails(101);
        assertTrue(details2.contains("No purchase contract found with this ID."));
    }

    /**
     * Tests the validation of rental periods.
     * 
     * @throws Exception if the method cannot be accessed or invoked
     */
    @Test
    public void validateRentalPeriod_Valid() throws Exception {
        Method method = ContractManager.class.getDeclaredMethod("validateRentalPeriod", LocalDate.class, LocalDate.class);
        method.setAccessible(true);
        assertTrue((Boolean) method.invoke(null, mockStartDate, mockEndDate));
    }

    @Test
    public void validateRentalPeriod_Invalid() throws Exception {
        Method method = ContractManager.class.getDeclaredMethod("validateRentalPeriod", LocalDate.class, LocalDate.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(null, mockEndDate, mockStartDate));
    }

    @Test
    public void testGettersAndSetters() throws ParseException {
        Contract contract = new Contract(1, mockCustomer, mockSaleVehicle, mockRentVehicle, false,
                LocalDate.now(), mockStartDate, mockEndDate);

        contract.setContractId(2);
        assertEquals(2, contract.getContractId());

        Customer newCustomer = new Customer(2, "John", "Doe", "john.doe@example.com",
                sdf.parse("02/02/1990"), false, new CustomerAddress(2, "AnotherTown", "456", "67890", "Country"));
        contract.setCustomer(newCustomer);
        assertEquals(newCustomer, contract.getCustomer());

        SaleVehicle newSaleVehicle = new SaleVehicle(3, new BigDecimal("25000.00"), true);
        contract.setSaleVehicle(newSaleVehicle);
        assertEquals(newSaleVehicle, contract.getSaleVehicle());

        RentVehicle newRentVehicle = new RentVehicle(4, true, new BigDecimal("600.00"), "Audi", new BigDecimal("6"));
        contract.setRentVehicle(newRentVehicle);
        assertEquals(newRentVehicle, contract.getRentVehicle());

        contract.setRentalContract(true);
        assertTrue(contract.isRentalContract());

        LocalDate newContractDate = LocalDate.now().plusDays(1);
        contract.setContractDate(newContractDate);
        assertEquals(newContractDate, contract.getContractDate());

        LocalDate newRentalStartDate = LocalDate.now().plusDays(2);
        contract.setRentalStartDate(newRentalStartDate);
        assertEquals(newRentalStartDate, contract.getRentalStartDate());

        LocalDate newRentalEndDate = LocalDate.now().plusDays(3);
        contract.setRentalEndDate(newRentalEndDate);
        assertEquals(newRentalEndDate, contract.getRentalEndDate());
    }
}
