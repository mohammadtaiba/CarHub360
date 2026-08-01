package de.fherfurt.carhub360.maintenance;

import de.fherfurt.carhub360.maintenance.dto.MaintenanceCreateRequest;
import de.fherfurt.carhub360.maintenance.dto.MaintenanceResponse;
import de.fherfurt.carhub360.maintenance.dto.MaintenanceUpdateRequest;
import de.fherfurt.carhub360.vehicle.Vehicle;
import de.fherfurt.carhub360.vehicle.VehicleRepository;
import de.fherfurt.carhub360.vehicle.VehicleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
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

class MaintenanceResourceTest {

    private static EntityManagerFactory emf;

    private EntityManager em;
    private VehicleService vehicleService;
    private MaintenanceResource maintenanceResource;

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
        MaintenanceRepository maintenanceRepository = new MaintenanceRepository();
        inject(vehicleRepository, "em", em);
        inject(maintenanceRepository, "em", em);

        vehicleService = new VehicleService();
        inject(vehicleService, "vehicleRepository", vehicleRepository);

        MaintenanceService maintenanceService = new MaintenanceService();
        inject(maintenanceService, "maintenanceRepository", maintenanceRepository);
        inject(maintenanceService, "vehicleRepository", vehicleRepository);

        maintenanceResource = new MaintenanceResource();
        inject(maintenanceResource, "maintenanceService", maintenanceService);
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    void createListUpdateAndDeleteMaintenanceRecord() {
        Vehicle vehicle = vehicleService.create(vehicle());
        em.flush();

        Response createResponse = maintenanceResource.createMaintenance(maintenanceRequest(vehicle.getVehicleId()));

        assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());
        MaintenanceResponse created = (MaintenanceResponse) createResponse.getEntity();
        assertTrue(created.getMaintenanceId() > 0);
        assertEquals(vehicle.getVehicleId(), created.getVehicleId());
        assertEquals(BigDecimal.valueOf(99.99), created.getMaintenanceCost());

        Response listResponse = maintenanceResource.getMaintenanceRecords(vehicle.getVehicleId());
        List<MaintenanceResponse> records = (List<MaintenanceResponse>) listResponse.getEntity();
        assertEquals(1, records.size());

        MaintenanceUpdateRequest updateRequest = updateMaintenanceRequest(vehicle.getVehicleId());
        Response updateResponse = maintenanceResource.updateMaintenance(created.getMaintenanceId(), updateRequest);
        MaintenanceResponse updated = (MaintenanceResponse) updateResponse.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus());
        assertEquals(BigDecimal.valueOf(129.50), updated.getMaintenanceCost());
        assertEquals("Brake service", updated.getMaintenanceDescription());

        Response deleteResponse = maintenanceResource.deleteMaintenance(created.getMaintenanceId());

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
    }

    @Test
    void missingMaintenanceReturnsNotFoundResponses() {
        assertNotFound(maintenanceResource.getMaintenance(999), "Maintenance record not found.");
        assertNotFound(
                maintenanceResource.updateMaintenance(999, updateMaintenanceRequest(999)),
                "Maintenance record not found."
        );
        assertNotFound(maintenanceResource.deleteMaintenance(999), "Maintenance record not found.");
    }

    private MaintenanceCreateRequest maintenanceRequest(int vehicleId) {
        MaintenanceCreateRequest request = new MaintenanceCreateRequest();
        request.setVehicleId(vehicleId);
        request.setMaintenanceStartDate(new Date());
        request.setMaintenanceEndDate(null);
        request.setMaintenanceCost(BigDecimal.valueOf(99.99));
        request.setMaintenanceDescription("Oil service");
        return request;
    }

    private MaintenanceUpdateRequest updateMaintenanceRequest(int vehicleId) {
        MaintenanceUpdateRequest request = new MaintenanceUpdateRequest();
        request.setVehicleId(vehicleId);
        request.setMaintenanceStartDate(new Date());
        request.setMaintenanceEndDate(null);
        request.setMaintenanceCost(BigDecimal.valueOf(129.50));
        request.setMaintenanceDescription("Brake service");
        return request;
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
