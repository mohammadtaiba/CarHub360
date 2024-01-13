package de.fherfurt.contract;

import de.fherfurt.RentVehicle.RentVehicle;
import de.fherfurt.SaleVehicle.SaleVehicle;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import static org.junit.Assert.*;

public class ContractTest
{

    private Contract contract;
    private Customer mockCustomer;
    private RentVehicle mockRentVehicle;
    private SaleVehicle mockSaleVehicle;
    private LocalDate mockStartDate;
    private LocalDate mockEndDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Before
    public void setUp() throws Exception {
        contract = new Contract();

        mockCustomer = new Customer(1, "Mohammad", "Taiba", "mohammadtaiba55@gmail.com",
                sdf.parse("01/01/1999"), false);

        mockRentVehicle = new RentVehicle(1, "BMW", "M5", 50000, 2023,
                "Sport-Car", 2, 1, true, 100, "XYZ123", 500.0f);

        mockSaleVehicle = new SaleVehicle(2, "Barcedes", "Benz", 30000, 2018, "PKW", 15000.0f, true);

        mockStartDate = LocalDate.now().plusDays(1); // Start date in the future
        mockEndDate = mockStartDate.plusDays(7); // End date one week later
    }

    @Test
    public void createPurchaseContract_Success()
    {
        assertTrue(contract.createPurchaseContract(1, mockCustomer, mockSaleVehicle));
    }

    @Test
    public void createRentalContract_Success()
    {
        assertTrue(contract.createRentalContract(2, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate));
    }

    @Test
    public void createRentalContract_Failure()
    {
        assertFalse(contract.createRentalContract(2, mockCustomer, mockRentVehicle, mockEndDate, mockStartDate)); // End date before start date
        assertFalse(contract.createRentalContract(-1, mockCustomer, mockRentVehicle, mockEndDate, mockStartDate));
        assertFalse(contract.createRentalContract(2, null, mockRentVehicle, mockEndDate, mockStartDate));
        assertFalse(contract.createRentalContract(2, mockCustomer, null, mockEndDate, mockStartDate));
        assertFalse(contract.createRentalContract(2, mockCustomer, mockRentVehicle, null, mockStartDate));
        assertFalse(contract.createRentalContract(2, mockCustomer, mockRentVehicle, mockEndDate, null));
    }

    @Test
    public void terminateRentalContract_Success()
    {
        contract.createRentalContract(3, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertTrue(contract.terminateRentalContract(3));
    }

    @Test
    public void terminateRentalContract_Failure()
    {
        contract.createRentalContract(3, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertFalse(contract.terminateRentalContract(2));
    }

    @Test
    public void renewRentalContract_Success()
    {
        contract.createRentalContract(4, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertTrue(contract.renewRentalContract(4, mockEndDate.plusDays(7)));
    }

    @Test
    public void renewRentalContract_Failure()
    {
        contract.createRentalContract(4, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        assertFalse(contract.renewRentalContract(4, mockEndDate.minusDays(8)));
    }

    @Test
    public void getTotalPrice_Valid()
    {
        contract.createRentalContract(5, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        double totalPrice = contract.getTotalPrice(5);
        assertEquals(700, totalPrice, 0.01);
    }

    @Test
    public void getRentalContractDetails_Valid()
    {
        contract.createRentalContract(6, mockCustomer, mockRentVehicle, mockStartDate, mockEndDate);
        String details = contract.getRentalContractDetails(6);
        assertNotNull(details);
        assertTrue(details.contains("Contract ID: 6"));
    }

    @Test
    public void getPurchaseContractDetails_Valid()
    {
        contract.createPurchaseContract(7, mockCustomer, mockSaleVehicle);
        String details = contract.getPurchaseContractDetails(7);
        assertNotNull(details);
        assertTrue(details.contains("Contract ID: 7"));
    }

    @Test
    public void validateRentalPeriod_Valid()
    {
        assertTrue(Contract.validateRentalPeriod(mockStartDate, mockEndDate));
    }

    @Test
    public void validateRentalPeriod_Invalid()
    {
        assertFalse(Contract.validateRentalPeriod(mockEndDate, mockStartDate));
    }
}
