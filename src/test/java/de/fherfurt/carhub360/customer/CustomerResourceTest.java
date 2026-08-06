package de.fherfurt.carhub360.customer;

import de.fherfurt.carhub360.customer.address.dto.CustomerAddressRequest;
import de.fherfurt.carhub360.customer.dto.CustomerCreateRequest;
import de.fherfurt.carhub360.customer.dto.CustomerResponse;
import de.fherfurt.carhub360.customer.dto.CustomerUpdateRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.fherfurt.carhub360.testsupport.InjectionSupport.inject;
import static de.fherfurt.carhub360.testsupport.ResponseAssertions.assertNotFound;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerResourceTest {

    private static EntityManagerFactory emf;

    private EntityManager em;
    private CustomerResource resource;

    @BeforeAll
    static void init() {
        emf = Persistence.createEntityManagerFactory("testPU");
    }

    @AfterAll
    static void closeFactory() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        CustomerRepository customerRepository = new CustomerRepository();
        inject(customerRepository, "em", em);

        CustomerService customerService = new CustomerService();
        inject(customerService, "customerRepository", customerRepository);
        inject(customerService, "customerValidator", new CustomerValidator());
        CustomerEmailUniquenessService customerEmailUniquenessService = new CustomerEmailUniquenessService();
        inject(customerEmailUniquenessService, "customerRepository", customerRepository);
        inject(customerService, "customerEmailUniquenessService", customerEmailUniquenessService);
        inject(customerService, "customerProfileUpdater", new CustomerProfileUpdater());

        resource = new CustomerResource();
        inject(resource, "customerService", customerService);
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    void createListAndSoftDeleteCustomer() {
        Response createResponse = resource.createCustomer(customerRequest("john@example.com"));

        assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());
        CustomerResponse created = (CustomerResponse) createResponse.getEntity();
        assertTrue(created.getCustomerId() > 0);
        assertEquals("John", created.getFirstName());
        assertNotNull(created.getCustomerAddress());

        Response listResponse = resource.getAllCustomers();
        List<CustomerResponse> customers = (List<CustomerResponse>) listResponse.getEntity();
        assertEquals(1, customers.size());

        Response deleteResponse = resource.deleteCustomer(created.getCustomerId());
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());

        Response getDeletedResponse = resource.getCustomer(created.getCustomerId());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), getDeletedResponse.getStatus());
    }

    @Test
    void duplicateEmailIsRejected() {
        resource.createCustomer(customerRequest("john@example.com"));

        Response duplicateResponse = resource.createCustomer(customerRequest("john@example.com"));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), duplicateResponse.getStatus());
    }

    @Test
    void missingCustomerReturnsNotFoundResponses() {
        assertNotFound(resource.getCustomer(999), "Customer not found.");
        assertNotFound(
                resource.updateCustomer(999, customerUpdateRequest("missing@example.com")),
                "Customer not found."
        );
        assertNotFound(resource.deleteCustomer(999), "Customer not found.");
    }

    private CustomerCreateRequest customerRequest(String email) {
        CustomerAddressRequest address = new CustomerAddressRequest();
        address.setCity("Erfurt");
        address.setPostalCode("99084");
        address.setStreet("Bahnhofstrasse");
        address.setStreetNumber("1");

        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail(email);
        request.setBirthdate(new Date());
        request.setFemale(Boolean.FALSE);
        request.setAddress(address);
        return request;
    }

    private CustomerUpdateRequest customerUpdateRequest(String email) {
        CustomerAddressRequest address = new CustomerAddressRequest();
        address.setCity("Erfurt");
        address.setPostalCode("99084");
        address.setStreet("Bahnhofstrasse");
        address.setStreetNumber("2");

        CustomerUpdateRequest request = new CustomerUpdateRequest();
        request.setFirstName("Jane");
        request.setLastName("Doe");
        request.setEmail(email);
        request.setBirthdate(new Date());
        request.setFemale(Boolean.TRUE);
        request.setAddress(address);
        return request;
    }
}
