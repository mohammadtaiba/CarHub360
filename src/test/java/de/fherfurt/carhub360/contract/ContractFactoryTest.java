package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicle;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContractFactoryTest {

    private final ContractFactory factory = new ContractFactory();

    @Test
    void createBuildsSaleContract() {
        Customer customer = new Customer();
        SaleVehicle saleVehicle = new SaleVehicle();
        LocalDate contractDate = LocalDate.of(2026, 8, 6);

        Contract contract = factory.create(customer, saleVehicle, null, false, contractDate, null, null);

        assertSame(customer, contract.getCustomer());
        assertSame(saleVehicle, contract.getSaleVehicle());
        assertNull(contract.getRentVehicle());
        assertEquals(contractDate, contract.getContractDate());
        assertNull(contract.getRentalStartDate());
        assertNull(contract.getRentalEndDate());
    }

    @Test
    void createBuildsRentalContractWithDefaultContractDate() {
        Customer customer = new Customer();
        RentVehicle rentVehicle = new RentVehicle();
        LocalDate startDate = LocalDate.of(2026, 8, 6);
        LocalDate endDate = startDate.plusDays(3);
        LocalDate beforeCreate = LocalDate.now();

        Contract contract = factory.create(customer, null, rentVehicle, true, null, startDate, endDate);
        LocalDate afterCreate = LocalDate.now();

        assertSame(customer, contract.getCustomer());
        assertNull(contract.getSaleVehicle());
        assertSame(rentVehicle, contract.getRentVehicle());
        assertTrue(contract.isRentalContract());
        assertTrue(!contract.getContractDate().isBefore(beforeCreate));
        assertTrue(!contract.getContractDate().isAfter(afterCreate));
        assertEquals(startDate, contract.getRentalStartDate());
        assertEquals(endDate, contract.getRentalEndDate());
    }

    @Test
    void applyUpdatesExistingContractFields() {
        Contract contract = new Contract();
        Customer customer = new Customer();
        RentVehicle rentVehicle = new RentVehicle();
        LocalDate contractDate = LocalDate.of(2026, 8, 6);
        LocalDate startDate = contractDate.plusDays(1);
        LocalDate endDate = contractDate.plusDays(4);

        factory.apply(contract, customer, null, rentVehicle, true, contractDate, startDate, endDate);

        assertSame(customer, contract.getCustomer());
        assertNull(contract.getSaleVehicle());
        assertSame(rentVehicle, contract.getRentVehicle());
        assertTrue(contract.isRentalContract());
        assertEquals(contractDate, contract.getContractDate());
        assertEquals(startDate, contract.getRentalStartDate());
        assertEquals(endDate, contract.getRentalEndDate());
    }
}
