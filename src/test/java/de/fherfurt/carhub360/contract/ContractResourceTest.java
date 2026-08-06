package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.contract.dto.ContractCreateRequest;
import de.fherfurt.carhub360.contract.dto.ContractResponse;
import de.fherfurt.carhub360.contract.dto.ContractUpdateRequest;
import de.fherfurt.carhub360.customer.address.CustomerAddress;
import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.customer.CustomerEmailUniquenessService;
import de.fherfurt.carhub360.customer.CustomerRepository;
import de.fherfurt.carhub360.customer.CustomerService;
import de.fherfurt.carhub360.customer.CustomerValidator;
import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import de.fherfurt.carhub360.vehicle.rent.RentVehicleRepository;
import de.fherfurt.carhub360.vehicle.rent.RentVehicleService;
import de.fherfurt.carhub360.vehicle.rent.RentVehicleValidator;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicle;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicleRepository;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicleService;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicleValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.fherfurt.carhub360.testsupport.InjectionSupport.inject;
import static de.fherfurt.carhub360.testsupport.ResponseAssertions.assertNotFound;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContractResourceTest {

    private static EntityManagerFactory emf;

    private EntityManager em;
    private ContractResource contractResource;
    private CustomerService customerService;
    private SaleVehicleService saleVehicleService;
    private RentVehicleService rentVehicleService;

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
        SaleVehicleRepository saleVehicleRepository = new SaleVehicleRepository();
        RentVehicleRepository rentVehicleRepository = new RentVehicleRepository();
        ContractRepository contractRepository = new ContractRepository();
        inject(customerRepository, "em", em);
        inject(saleVehicleRepository, "em", em);
        inject(rentVehicleRepository, "em", em);
        inject(contractRepository, "em", em);

        customerService = new CustomerService();
        inject(customerService, "customerRepository", customerRepository);
        inject(customerService, "customerValidator", new CustomerValidator());
        CustomerEmailUniquenessService customerEmailUniquenessService = new CustomerEmailUniquenessService();
        inject(customerEmailUniquenessService, "customerRepository", customerRepository);
        inject(customerService, "customerEmailUniquenessService", customerEmailUniquenessService);

        saleVehicleService = new SaleVehicleService();
        inject(saleVehicleService, "repository", saleVehicleRepository);
        inject(saleVehicleService, "saleVehicleValidator", new SaleVehicleValidator());

        rentVehicleService = new RentVehicleService();
        inject(rentVehicleService, "repository", rentVehicleRepository);
        inject(rentVehicleService, "rentVehicleValidator", new RentVehicleValidator());

        ContractService contractService = new ContractService();
        inject(contractService, "contractRepository", contractRepository);
        ContractReferenceResolver contractReferenceResolver = new ContractReferenceResolver();
        inject(contractReferenceResolver, "customerRepository", customerRepository);
        inject(contractReferenceResolver, "saleVehicleRepository", saleVehicleRepository);
        inject(contractReferenceResolver, "rentVehicleRepository", rentVehicleRepository);
        inject(contractService, "contractReferenceResolver", contractReferenceResolver);
        inject(contractService, "contractValidator", new ContractValidator());
        inject(contractService, "contractPriceCalculator", new ContractPriceCalculator());
        ContractRentalVehicleService contractRentalVehicleService = new ContractRentalVehicleService();
        inject(contractRentalVehicleService, "rentVehicleRepository", rentVehicleRepository);
        inject(contractService, "contractRentalVehicleService", contractRentalVehicleService);
        inject(contractService, "contractFactory", new ContractFactory());

        contractResource = new ContractResource();
        inject(contractResource, "contractService", contractService);
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    void createSaleContractThroughServiceLayer() {
        Customer customer = customerService.create(customer("sale@example.com"));
        SaleVehicle saleVehicle = saleVehicleService.create(saleVehicle());
        em.flush();

        ContractCreateRequest request = new ContractCreateRequest();
        request.setCustomerId(customer.getCustomerId());
        request.setSaleVehicleId(saleVehicle.getVehicleId());
        request.setRentalContract(false);
        request.setContractDate(LocalDate.now());

        Response response = contractResource.createContract(request);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        ContractResponse created = (ContractResponse) response.getEntity();
        assertNotNull(created);
        assertEquals(customer.getCustomerId(), created.getCustomerId());
        assertEquals(saleVehicle.getVehicleId(), created.getSaleVehicleId());
    }

    @Test
    void rentalContractMarksVehicleUnavailableAndDeleteReleasesIt() {
        Customer customer = customerService.create(customer("rent@example.com"));
        RentVehicle rentVehicle = rentVehicleService.create(rentVehicle());
        em.flush();

        ContractCreateRequest request = new ContractCreateRequest();
        request.setCustomerId(customer.getCustomerId());
        request.setRentVehicleId(rentVehicle.getVehicleId());
        request.setRentalContract(true);
        request.setRentalStartDate(LocalDate.now());
        request.setRentalEndDate(LocalDate.now().plusDays(3));

        Response createResponse = contractResource.createContract(request);

        assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());
        em.flush();
        assertFalse(rentVehicleService.findById(rentVehicle.getVehicleId()).isAvailable());

        ContractResponse created = (ContractResponse) createResponse.getEntity();
        assertEquals(rentVehicle.getVehicleId(), created.getRentVehicleId());
        assertTrue(created.isRentalContract());

        int contractId = created.getContractId();
        Response deleteResponse = contractResource.deleteContract(contractId);

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
        assertTrue(rentVehicleService.findById(rentVehicle.getVehicleId()).isAvailable());
    }

    @Test
    void invalidContractShapeIsRejected() {
        Customer customer = customerService.create(customer("invalid@example.com"));
        SaleVehicle saleVehicle = saleVehicleService.create(saleVehicle());
        em.flush();

        ContractCreateRequest request = new ContractCreateRequest();
        request.setCustomerId(customer.getCustomerId());
        request.setSaleVehicleId(saleVehicle.getVehicleId());
        request.setRentalContract(true);
        request.setRentalStartDate(LocalDate.now());
        request.setRentalEndDate(LocalDate.now().plusDays(1));

        Response response = contractResource.createContract(request);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void missingContractReturnsNotFoundResponses() {
        assertNotFound(contractResource.getContract(999), "Contract not found.");
        assertNotFound(contractResource.getRentalPrice(999), "Contract not found.");
        assertNotFound(
                contractResource.updateContract(999, contractUpdateRequest()),
                "Contract not found."
        );
        assertNotFound(contractResource.deleteContract(999), "Contract not found.");
    }

    private ContractUpdateRequest contractUpdateRequest() {
        ContractUpdateRequest request = new ContractUpdateRequest();
        request.setCustomerId(999);
        request.setSaleVehicleId(999);
        request.setRentalContract(false);
        request.setContractDate(LocalDate.now());
        return request;
    }

    private Customer customer(String email) {
        CustomerAddress address = new CustomerAddress();
        address.setCity("Erfurt");
        address.setPostalCode("99084");
        address.setStreet("Anger");
        address.setStreetNumber("1");

        Customer customer = new Customer();
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setEmail(email);
        customer.setBirthdate(new Date());
        customer.setFemale(false);
        customer.setCustomerAddress(address);
        return customer;
    }

    private SaleVehicle saleVehicle() {
        SaleVehicle vehicle = new SaleVehicle();
        vehicle.setName("Golf");
        vehicle.setBrand("VW");
        vehicle.setKilometerCount(10000);
        vehicle.setConstructionYear(2021);
        vehicle.setType("Compact");
        vehicle.setSalePrice(BigDecimal.valueOf(18000));
        vehicle.setNewVehicle(false);
        return vehicle;
    }

    private RentVehicle rentVehicle() {
        RentVehicle vehicle = new RentVehicle();
        vehicle.setName("ID.3");
        vehicle.setBrand("VW");
        vehicle.setKilometerCount(5000);
        vehicle.setConstructionYear(2022);
        vehicle.setType("Electric");
        vehicle.setAvailable(true);
        vehicle.setDailyPrice(BigDecimal.valueOf(79));
        vehicle.setDeposit(BigDecimal.valueOf(500));
        vehicle.setLicensePlate("EF-CH-360");
        return vehicle;
    }
}
