package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.customer.CustomerRepository;
import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import de.fherfurt.carhub360.vehicle.rent.RentVehicleRepository;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicle;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicleRepository;
import org.junit.jupiter.api.Test;

import static de.fherfurt.carhub360.testsupport.InjectionSupport.inject;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ContractReferenceResolverTest {

    private final FakeCustomerRepository customerRepository = new FakeCustomerRepository();
    private final FakeSaleVehicleRepository saleVehicleRepository = new FakeSaleVehicleRepository();
    private final FakeRentVehicleRepository rentVehicleRepository = new FakeRentVehicleRepository();
    private final ContractReferenceResolver resolver = new ContractReferenceResolver();

    ContractReferenceResolverTest() {
        inject(resolver, "customerRepository", customerRepository);
        inject(resolver, "saleVehicleRepository", saleVehicleRepository);
        inject(resolver, "rentVehicleRepository", rentVehicleRepository);
    }

    @Test
    void resolveReturnsRequestedReferences() {
        Customer customer = new Customer();
        SaleVehicle saleVehicle = new SaleVehicle();
        RentVehicle rentVehicle = new RentVehicle();
        customerRepository.customer = customer;
        saleVehicleRepository.saleVehicle = saleVehicle;
        rentVehicleRepository.rentVehicle = rentVehicle;

        ContractReferences references = resolver.resolve(1, 2, 3);

        assertSame(customer, references.customer());
        assertSame(saleVehicle, references.saleVehicle());
        assertSame(rentVehicle, references.rentVehicle());
    }

    @Test
    void resolveSkipsOptionalVehicleLookups() {
        Customer customer = new Customer();
        customerRepository.customer = customer;

        ContractReferences references = resolver.resolve(1, null, null);

        assertSame(customer, references.customer());
        assertNull(references.saleVehicle());
        assertNull(references.rentVehicle());
    }

    private static class FakeCustomerRepository extends CustomerRepository {

        private Customer customer;

        @Override
        public Customer findById(int customerId) {
            return customer;
        }
    }

    private static class FakeSaleVehicleRepository extends SaleVehicleRepository {

        private SaleVehicle saleVehicle;

        @Override
        public SaleVehicle findById(int saleVehicleId) {
            return saleVehicle;
        }
    }

    private static class FakeRentVehicleRepository extends RentVehicleRepository {

        private RentVehicle rentVehicle;

        @Override
        public RentVehicle findById(int rentVehicleId) {
            return rentVehicle;
        }
    }
}
