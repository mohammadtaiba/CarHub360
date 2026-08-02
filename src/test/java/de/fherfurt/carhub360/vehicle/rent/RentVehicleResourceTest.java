package de.fherfurt.carhub360.vehicle.rent;

import de.fherfurt.carhub360.vehicle.rent.dto.RentVehicleCreateRequest;
import de.fherfurt.carhub360.vehicle.rent.dto.RentVehicleResponse;
import de.fherfurt.carhub360.vehicle.rent.dto.RentVehicleUpdateRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.fherfurt.carhub360.testsupport.InjectionSupport.inject;
import static de.fherfurt.carhub360.testsupport.ResponseAssertions.assertNotFound;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RentVehicleResourceTest {

    private static EntityManagerFactory emf;

    private EntityManager em;
    private RentVehicleResource rentVehicleResource;

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

        RentVehicleRepository rentVehicleRepository = new RentVehicleRepository();
        inject(rentVehicleRepository, "em", em);

        RentVehicleService rentVehicleService = new RentVehicleService();
        inject(rentVehicleService, "repository", rentVehicleRepository);

        rentVehicleResource = new RentVehicleResource();
        inject(rentVehicleResource, "rentVehicleService", rentVehicleService);
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    void createListUpdateAndDeleteRentVehicle() {
        Response createResponse = rentVehicleResource.createRentVehicle(rentVehicleRequest());

        assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());
        RentVehicleResponse created = (RentVehicleResponse) createResponse.getEntity();
        assertTrue(created.getVehicleId() > 0);
        assertEquals("EF-CH-360", created.getLicensePlate());
        assertTrue(created.isAvailable());

        Response listResponse = rentVehicleResource.getRentVehicles();
        List<RentVehicleResponse> vehicles = (List<RentVehicleResponse>) listResponse.getEntity();
        assertEquals(1, vehicles.size());

        Response getResponse = rentVehicleResource.getRentVehicle(created.getVehicleId());
        assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());

        Response updateResponse = rentVehicleResource.updateRentVehicle(created.getVehicleId(), updateRentVehicleRequest());
        RentVehicleResponse updated = (RentVehicleResponse) updateResponse.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus());
        assertEquals(BigDecimal.valueOf(89), updated.getDailyPrice());
        assertEquals("EF-RE-360", updated.getLicensePlate());
        assertFalse(updated.isAvailable());

        Response deleteResponse = rentVehicleResource.deleteRentVehicle(created.getVehicleId());

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
    }

    @Test
    void missingRentVehicleReturnsNotFoundResponses() {
        assertNotFound(rentVehicleResource.getRentVehicle(999), "Rent vehicle not found.");
        assertNotFound(
                rentVehicleResource.updateRentVehicle(999, updateRentVehicleRequest()),
                "Rent vehicle not found."
        );
        assertNotFound(rentVehicleResource.deleteRentVehicle(999), "Rent vehicle not found.");
    }

    private RentVehicleCreateRequest rentVehicleRequest() {
        RentVehicleCreateRequest request = new RentVehicleCreateRequest();
        request.setName("ID.3");
        request.setBrand("VW");
        request.setKilometerCount(5000);
        request.setConstructionYear(2022);
        request.setType("Electric");
        request.setAvailable(true);
        request.setDailyPrice(BigDecimal.valueOf(79));
        request.setLicensePlate("EF-CH-360");
        request.setDeposit(BigDecimal.valueOf(500));
        return request;
    }

    private RentVehicleUpdateRequest updateRentVehicleRequest() {
        RentVehicleUpdateRequest request = new RentVehicleUpdateRequest();
        request.setName("ID.4");
        request.setBrand("VW");
        request.setKilometerCount(7000);
        request.setConstructionYear(2023);
        request.setType("Electric SUV");
        request.setAvailable(false);
        request.setDailyPrice(BigDecimal.valueOf(89));
        request.setLicensePlate("EF-RE-360");
        request.setDeposit(BigDecimal.valueOf(650));
        return request;
    }
}
