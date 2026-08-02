package de.fherfurt.carhub360.vehicle.sale;

import de.fherfurt.carhub360.vehicle.sale.dto.SaleVehicleCreateRequest;
import de.fherfurt.carhub360.vehicle.sale.dto.SaleVehicleResponse;
import de.fherfurt.carhub360.vehicle.sale.dto.SaleVehicleUpdateRequest;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaleVehicleResourceTest {

    private static EntityManagerFactory emf;

    private EntityManager em;
    private SaleVehicleResource saleVehicleResource;

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

        SaleVehicleRepository saleVehicleRepository = new SaleVehicleRepository();
        inject(saleVehicleRepository, "em", em);

        SaleVehicleService saleVehicleService = new SaleVehicleService();
        inject(saleVehicleService, "repository", saleVehicleRepository);

        saleVehicleResource = new SaleVehicleResource();
        inject(saleVehicleResource, "saleVehicleService", saleVehicleService);
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    void createListUpdateAndDeleteSaleVehicle() {
        Response createResponse = saleVehicleResource.createSaleVehicle(saleVehicleRequest());

        assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());
        SaleVehicleResponse created = (SaleVehicleResponse) createResponse.getEntity();
        assertTrue(created.getVehicleId() > 0);
        assertEquals(BigDecimal.valueOf(18000), created.getSalePrice());
        assertEquals("Golf", created.getName());

        Response listResponse = saleVehicleResource.getSaleVehicles();
        List<SaleVehicleResponse> vehicles = (List<SaleVehicleResponse>) listResponse.getEntity();
        assertEquals(1, vehicles.size());

        Response getResponse = saleVehicleResource.getSaleVehicle(created.getVehicleId());
        assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());

        Response updateResponse = saleVehicleResource.updateSaleVehicle(created.getVehicleId(), updateSaleVehicleRequest());
        SaleVehicleResponse updated = (SaleVehicleResponse) updateResponse.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus());
        assertEquals(BigDecimal.valueOf(22000), updated.getSalePrice());
        assertTrue(updated.isNewVehicle());

        Response deleteResponse = saleVehicleResource.deleteSaleVehicle(created.getVehicleId());

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
    }

    @Test
    void missingSaleVehicleReturnsNotFoundResponses() {
        assertNotFound(saleVehicleResource.getSaleVehicle(999), "Sale vehicle not found.");
        assertNotFound(
                saleVehicleResource.updateSaleVehicle(999, updateSaleVehicleRequest()),
                "Sale vehicle not found."
        );
        assertNotFound(saleVehicleResource.deleteSaleVehicle(999), "Sale vehicle not found.");
    }

    private SaleVehicleCreateRequest saleVehicleRequest() {
        SaleVehicleCreateRequest request = new SaleVehicleCreateRequest();
        request.setName("Golf");
        request.setBrand("VW");
        request.setKilometerCount(12000);
        request.setConstructionYear(2021);
        request.setType("Compact");
        request.setSalePrice(BigDecimal.valueOf(18000));
        request.setNewVehicle(false);
        return request;
    }

    private SaleVehicleUpdateRequest updateSaleVehicleRequest() {
        SaleVehicleUpdateRequest request = new SaleVehicleUpdateRequest();
        request.setName("Golf Variant");
        request.setBrand("VW");
        request.setKilometerCount(500);
        request.setConstructionYear(2024);
        request.setType("Combi");
        request.setSalePrice(BigDecimal.valueOf(22000));
        request.setNewVehicle(true);
        return request;
    }
}
