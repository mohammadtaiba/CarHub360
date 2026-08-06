package de.fherfurt.carhub360.vehicle;

import de.fherfurt.carhub360.vehicle.dto.VehicleCreateRequest;
import de.fherfurt.carhub360.vehicle.dto.VehicleResponse;
import de.fherfurt.carhub360.vehicle.dto.VehicleUpdateRequest;
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
import static de.fherfurt.carhub360.testsupport.ResponseAssertions.assertNotFound;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehicleResourceTest {

    private static EntityManagerFactory emf;

    private EntityManager em;
    private VehicleResource vehicleResource;

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

        VehicleRepository vehicleRepository = new VehicleRepository();
        inject(vehicleRepository, "em", em);

        VehicleService vehicleService = new VehicleService();
        inject(vehicleService, "vehicleRepository", vehicleRepository);
        inject(vehicleService, "vehicleValidator", new VehicleValidator());

        vehicleResource = new VehicleResource();
        inject(vehicleResource, "vehicleService", vehicleService);
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    void createListUpdateAndDeleteVehicle() {
        Response createResponse = vehicleResource.createVehicle(vehicleRequest());

        assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());
        VehicleResponse created = (VehicleResponse) createResponse.getEntity();
        assertTrue(created.getVehicleId() > 0);
        assertEquals("Octavia", created.getName());
        assertEquals("Skoda", created.getBrand());

        Response listResponse = vehicleResource.getVehicles();
        List<VehicleResponse> vehicles = (List<VehicleResponse>) listResponse.getEntity();
        assertEquals(1, vehicles.size());

        Response getResponse = vehicleResource.getVehicle(created.getVehicleId());
        assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());

        Response updateResponse = vehicleResource.updateVehicle(created.getVehicleId(), updateVehicleRequest());
        VehicleResponse updated = (VehicleResponse) updateResponse.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus());
        assertEquals("Superb", updated.getName());
        assertEquals(51000, updated.getKilometerCount());

        Response deleteResponse = vehicleResource.deleteVehicle(created.getVehicleId());

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
    }

    @Test
    void missingVehicleReturnsNotFoundResponses() {
        assertNotFound(vehicleResource.getVehicle(999), "Vehicle not found.");
        assertNotFound(vehicleResource.updateVehicle(999, updateVehicleRequest()), "Vehicle not found.");
        assertNotFound(vehicleResource.deleteVehicle(999), "Vehicle not found.");
    }

    private VehicleCreateRequest vehicleRequest() {
        VehicleCreateRequest request = new VehicleCreateRequest();
        request.setName("Octavia");
        request.setBrand("Skoda");
        request.setKilometerCount(42000);
        request.setConstructionYear(2020);
        request.setType("Combi");
        return request;
    }

    private VehicleUpdateRequest updateVehicleRequest() {
        VehicleUpdateRequest request = new VehicleUpdateRequest();
        request.setName("Superb");
        request.setBrand("Skoda");
        request.setKilometerCount(51000);
        request.setConstructionYear(2021);
        request.setType("Limousine");
        return request;
    }
}
