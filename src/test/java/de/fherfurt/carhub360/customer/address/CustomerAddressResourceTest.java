package de.fherfurt.carhub360.customer.address;

import de.fherfurt.carhub360.customer.address.dto.CustomerAddressCreateRequest;
import de.fherfurt.carhub360.customer.address.dto.CustomerAddressResponse;
import de.fherfurt.carhub360.customer.address.dto.CustomerAddressUpdateRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.fherfurt.carhub360.testsupport.InjectionSupport.inject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerAddressResourceTest {

    private static EntityManagerFactory emf;

    private EntityManager em;
    private CustomerAddressResource customerAddressResource;

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

        CustomerAddressRepository customerAddressRepository = new CustomerAddressRepository();
        inject(customerAddressRepository, "em", em);

        CustomerAddressService customerAddressService = new CustomerAddressService();
        inject(customerAddressService, "repository", customerAddressRepository);

        customerAddressResource = new CustomerAddressResource();
        inject(customerAddressResource, "customerAddressService", customerAddressService);
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    void createListUpdateAndDeleteAddress() {
        Response createResponse = customerAddressResource.createAddress(addressRequest());

        assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());
        CustomerAddressResponse created = (CustomerAddressResponse) createResponse.getEntity();
        assertTrue(created.getAddressId() > 0);
        assertEquals("Erfurt", created.getCity());
        assertEquals("99084", created.getPostalCode());

        Response listResponse = customerAddressResource.getAddresses();
        List<CustomerAddressResponse> addresses = (List<CustomerAddressResponse>) listResponse.getEntity();
        assertEquals(1, addresses.size());

        CustomerAddressUpdateRequest updateRequest = updateAddressRequest();
        Response updateResponse = customerAddressResource.updateAddress(created.getAddressId(), updateRequest);
        CustomerAddressResponse updated = (CustomerAddressResponse) updateResponse.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus());
        assertEquals("Weimar", updated.getCity());
        assertEquals("99423", updated.getPostalCode());

        Response deleteResponse = customerAddressResource.deleteAddress(created.getAddressId());

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
    }

    private CustomerAddressCreateRequest addressRequest() {
        CustomerAddressCreateRequest request = new CustomerAddressCreateRequest();
        request.setCity("Erfurt");
        request.setPostalCode("99084");
        request.setStreet("Domplatz");
        request.setStreetNumber("1");
        return request;
    }

    private CustomerAddressUpdateRequest updateAddressRequest() {
        CustomerAddressUpdateRequest request = new CustomerAddressUpdateRequest();
        request.setCity("Weimar");
        request.setPostalCode("99423");
        request.setStreet("Goetheplatz");
        request.setStreetNumber("2");
        return request;
    }
}
