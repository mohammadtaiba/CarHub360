package de.fherfurt.core;

import de.fherfurt.logic.RentVehicle;
import de.fherfurt.logic.SaleVehicle;
import de.fherfurt.core.Customer;
import de.fherfurt.logic.CustomerAddress;
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

        mockStartDate = LocalDate.now().plusDays(1);
        mockEndDate = mockStartDate.plusDays(7);
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
        assertFalse(Contract.createRentalContract(2, mockCustomer, mockRentVehicle, mockEndDate, mockStartDate));
        assertFalse(Contract.createRentalContract(-1, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate));
        assertFalse(Contract.createRentalContract(2, mockCustomer, null, mockStartDate, mockEndDate));
        assertFalse(Contract.createRentalContract(2, mockCustomer, mockRentVehicle, null, mockEndDate));
        assertFalse(Contract.createRentalContract(2, mockCustomer, mockRentVehicle, mockStartDate, null));
    }

    @Test
    public void terminateRentalContract_Success() {
        Contract.createRentalContract(3, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertTrue(Contract.terminateRentalContract(3));
    }

    @Test
    public void terminateRentalContract_Failure() {
        Contract.createRentalContract(3, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertFalse(Contract.terminateRentalContract(2));
    }

    @Test
    public void renewRentalContract_Success() {
        Contract.createRentalContract(4, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertTrue(Contract.renewRentalContract(4, mockEndDate.plusDays(7)));
    }

    @Test
    public void renewRentalContract_Failure() {
        Contract.createRentalContract(4, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertFalse(Contract.renewRentalContract(4, mockEndDate.minusDays(8)));
    }

    @Test
    public void getTotalPrice_Valid() {
        Contract.createRentalContract(5, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);

        BigDecimal totalPrice = Contract.getTotalPrice(5);
        BigDecimal expectedPrice = BigDecimal.valueOf(3500.0);
        assertTrue(totalPrice.compareTo(expectedPrice) == 0);

        BigDecimal totalPrice2 = Contract.getTotalPrice(90);
        BigDecimal expectedPrice2 = BigDecimal.valueOf(-1);
        assertTrue(totalPrice2.compareTo(expectedPrice2) == 0);
    }

    @Test
    public void getRentalContractDetails_Valid() {
        Contract.createRentalContract(6, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        String details = Contract.getRentalContractDetails(6);
        assertNotNull(details);
        assertTrue(details.contains("Contract ID: 6"));
        String details2 = Contract.getRentalContractDetails(100);
        assertTrue(details2.contains("No rental contract found with this ID."));
    }

    @Test
    public void getPurchaseContractDetails_Valid() {
        Contract.createPurchaseContract(7, mockCustomer, mockSaleVehicle);
        String details = Contract.getPurchaseContractDetails(7);
        assertNotNull(details);
        assertTrue(details.contains("Contract ID: 7"));
        String details2 = Contract.getPurchaseContractDetails(101);
        assertTrue(details2.contains("No purchase contract found with this ID."));
    }

    /**
     * Tests the validation of rental periods.
     * 
     * @throws Exception if the method cannot be accessed or invoked
     */
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
