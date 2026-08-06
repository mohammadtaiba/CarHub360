package de.fherfurt.carhub360.customer;

import org.junit.jupiter.api.Test;

import static de.fherfurt.carhub360.testsupport.InjectionSupport.inject;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerEmailUniquenessServiceTest {

    private final FakeCustomerRepository repository = new FakeCustomerRepository();
    private final CustomerEmailUniquenessService service = new CustomerEmailUniquenessService();

    CustomerEmailUniquenessServiceTest() {
        inject(service, "customerRepository", repository);
    }

    @Test
    void requireUniqueForCreateAcceptsUnusedEmail() {
        assertDoesNotThrow(() -> service.requireUniqueForCreate("alex@example.com"));
    }

    @Test
    void requireUniqueForCreateRejectsDuplicateEmail() {
        repository.customer = customer(1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.requireUniqueForCreate("alex@example.com")
        );

        assertEquals("A customer with this email already exists.", exception.getMessage());
    }

    @Test
    void requireUniqueForUpdateAcceptsSameCustomerEmail() {
        repository.customer = customer(1);

        assertDoesNotThrow(() -> service.requireUniqueForUpdate(1, "alex@example.com"));
    }

    @Test
    void requireUniqueForUpdateRejectsOtherCustomerEmail() {
        repository.customer = customer(2);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.requireUniqueForUpdate(1, "alex@example.com")
        );

        assertEquals("A customer with this email already exists.", exception.getMessage());
    }

    private Customer customer(int customerId) {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        return customer;
    }

    private static class FakeCustomerRepository extends CustomerRepository {

        private Customer customer;

        @Override
        public Customer findByEmail(String email) {
            return customer;
        }
    }
}
