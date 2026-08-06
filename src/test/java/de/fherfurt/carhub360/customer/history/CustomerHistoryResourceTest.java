package de.fherfurt.carhub360.customer.history;

import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.customer.CustomerRepository;
import de.fherfurt.carhub360.customer.CustomerService;
import de.fherfurt.carhub360.customer.CustomerValidator;
import de.fherfurt.carhub360.customer.address.CustomerAddress;
import de.fherfurt.carhub360.customer.history.dto.CustomerHistoryCreateRequest;
import de.fherfurt.carhub360.customer.history.dto.CustomerHistoryResponse;
import de.fherfurt.carhub360.customer.history.dto.CustomerHistoryUpdateRequest;
import de.fherfurt.carhub360.vehicle.Vehicle;
import de.fherfurt.carhub360.vehicle.VehicleRepository;
import de.fherfurt.carhub360.vehicle.VehicleService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerHistoryResourceTest {

    private static EntityManagerFactory emf;

    private EntityManager em;
    private CustomerService customerService;
    private VehicleService vehicleService;
    private CustomerHistoryResource customerHistoryResource;

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
        VehicleRepository vehicleRepository = new VehicleRepository();
        CustomerHistoryRepository historyRepository = new CustomerHistoryRepository();
        inject(customerRepository, "em", em);
        inject(vehicleRepository, "em", em);
        inject(historyRepository, "em", em);

        customerService = new CustomerService();
        inject(customerService, "customerRepository", customerRepository);
        inject(customerService, "customerValidator", new CustomerValidator());

        vehicleService = new VehicleService();
        inject(vehicleService, "vehicleRepository", vehicleRepository);

        CustomerHistoryService customerHistoryService = new CustomerHistoryService();
        inject(customerHistoryService, "repository", historyRepository);
        inject(customerHistoryService, "customerRepository", customerRepository);
        inject(customerHistoryService, "vehicleRepository", vehicleRepository);
        inject(customerHistoryService, "customerHistoryValidator", new CustomerHistoryValidator());

        customerHistoryResource = new CustomerHistoryResource();
        inject(customerHistoryResource, "customerHistoryService", customerHistoryService);
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    void createListUpdateAndDeleteCustomerHistoryRecord() {
        Customer customer = customerService.create(customer());
        Vehicle vehicle = vehicleService.create(vehicle());
        em.flush();

        Response createResponse = customerHistoryResource.createCustomerHistory(
                historyRequest(customer.getCustomerId(), vehicle.getVehicleId())
        );

        assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());
        CustomerHistoryResponse created = (CustomerHistoryResponse) createResponse.getEntity();
        assertTrue(created.getCustomerHistoryId() > 0);
        assertEquals(customer.getCustomerId(), created.getCustomerId());
        assertEquals(vehicle.getVehicleId(), created.getVehicleId());
        assertEquals(CustomerHistoryReview.FUENF, created.getCustomerHistoryReview());

        Response listResponse = customerHistoryResource.getCustomerHistories(customer.getCustomerId());
        List<CustomerHistoryResponse> histories = (List<CustomerHistoryResponse>) listResponse.getEntity();
        assertEquals(1, histories.size());

        CustomerHistoryUpdateRequest updateRequest = updateHistoryRequest(customer.getCustomerId(), vehicle.getVehicleId());
        Response updateResponse = customerHistoryResource.updateCustomerHistory(
                created.getCustomerHistoryId(),
                updateRequest
        );
        CustomerHistoryResponse updated = (CustomerHistoryResponse) updateResponse.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus());
        assertEquals(CustomerHistoryReview.VIER, updated.getCustomerHistoryReview());
        assertEquals("Follow-up handover review", updated.getDescription());

        Response deleteResponse = customerHistoryResource.deleteCustomerHistory(created.getCustomerHistoryId());

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
    }

    @Test
    void missingCustomerHistoryReturnsNotFoundResponses() {
        assertNotFound(
                customerHistoryResource.getCustomerHistory(999),
                "Customer history record not found."
        );
        assertNotFound(
                customerHistoryResource.updateCustomerHistory(999, updateHistoryRequest(999, 999)),
                "Customer history record not found."
        );
        assertNotFound(
                customerHistoryResource.deleteCustomerHistory(999),
                "Customer history record not found."
        );
    }

    private CustomerHistoryCreateRequest historyRequest(int customerId, int vehicleId) {
        CustomerHistoryCreateRequest request = new CustomerHistoryCreateRequest();
        request.setCustomerId(customerId);
        request.setVehicleId(vehicleId);
        request.setCustomerHistoryReview(CustomerHistoryReview.FUENF);
        request.setDescription("Successful vehicle handover");
        request.setActionDate(new Date());
        request.setForRentalCar(false);
        return request;
    }

    private CustomerHistoryUpdateRequest updateHistoryRequest(int customerId, int vehicleId) {
        CustomerHistoryUpdateRequest request = new CustomerHistoryUpdateRequest();
        request.setCustomerId(customerId);
        request.setVehicleId(vehicleId);
        request.setCustomerHistoryReview(CustomerHistoryReview.VIER);
        request.setDescription("Follow-up handover review");
        request.setActionDate(new Date());
        request.setForRentalCar(false);
        return request;
    }

    private Customer customer() {
        CustomerAddress address = new CustomerAddress();
        address.setCity("Erfurt");
        address.setPostalCode("99084");
        address.setStreet("Domplatz");
        address.setStreetNumber("1");

        Customer customer = new Customer();
        customer.setFirstName("Alex");
        customer.setLastName("Meyer");
        customer.setEmail("history@example.com");
        customer.setBirthdate(new Date());
        customer.setFemale(false);
        customer.setCustomerAddress(address);
        return customer;
    }

    private Vehicle vehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Octavia");
        vehicle.setBrand("Skoda");
        vehicle.setKilometerCount(42000);
        vehicle.setConstructionYear(2020);
        vehicle.setType("Combi");
        return vehicle;
    }
}
