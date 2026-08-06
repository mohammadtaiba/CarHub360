package de.fherfurt.carhub360.customer;

import org.junit.jupiter.api.Test;

import static de.fherfurt.carhub360.testsupport.InjectionSupport.inject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerReferenceServiceTest {

    private final FakeCustomerRepository repository = new FakeCustomerRepository();
    private final CustomerReferenceService service = new CustomerReferenceService();

    CustomerReferenceServiceTest() {
        inject(service, "customerRepository", repository);
    }

    @Test
    void requireActiveCustomerReturnsExistingActiveCustomer() {
        Customer customer = new Customer();
        repository.customer = customer;

        assertSame(customer, service.requireActiveCustomer(1));
    }

    @Test
    void requireActiveCustomerRejectsMissingCustomer() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.requireActiveCustomer(1)
        );

        assertEquals("customerId does not reference an active customer.", exception.getMessage());
    }

    @Test
    void requireActiveCustomerRejectsDeletedCustomer() {
        Customer customer = new Customer();
        customer.setDeleted(true);
        repository.customer = customer;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.requireActiveCustomer(1)
        );

        assertEquals("customerId does not reference an active customer.", exception.getMessage());
    }

    private static class FakeCustomerRepository extends CustomerRepository {

        private Customer customer;

        @Override
        public Customer findById(int customerId) {
            return customer;
        }
    }
}
