package de.fherfurt.api;


import de.fherfurt.api.ContractResource;
import de.fherfurt.core.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.core.Response;
import org.junit.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ContractResourceTest {
    private static EntityManagerFactory emf;
    private EntityManager em;
    private ContractResource resource;

    @BeforeClass
    public static void init() {
        emf = Persistence.createEntityManagerFactory("myPU");
    }

    @AfterClass
    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
        resource = new ContractResource();
        Field f = ContractResource.class.getDeclaredField("entityManager");
        f.setAccessible(true);
        f.set(resource, em);
        em.getTransaction().begin();
    }

    @After
    public void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    public void testCreateAndGetContract() {
        // Create and persist the address
        CustomerAddress address = new CustomerAddress();
        address.setCity("City");
        address.setPostalCode("12345");
        address.setStreet("Street");
        address.setStreetNumber("1");
        em.persist(address);
        em.flush();

        // Create and persist the customer
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@example.com");
        customer.setBirthdate(new java.util.Date());
        customer.setFemale(true);
        customer.setCustomerAddress(address);
        em.persist(customer);
        em.flush();

        // Create and persist the sale vehicle
        SaleVehicle saleVehicle = new SaleVehicle();
        saleVehicle.setSalePrice(BigDecimal.valueOf(10000));
        saleVehicle.setNew(true);
        em.persist(saleVehicle);
        em.flush();

        // Create and persist the contract
        Contract contract = new Contract();
        contract.setCustomer(customer);
        contract.setSaleVehicle(saleVehicle);
        contract.setRentVehicle(null);
        contract.setRentalContract(false);
        contract.setContractDate(LocalDate.now());
        contract.setRentalStartDate(null);
        contract.setRentalEndDate(null);
        em.persist(contract);
        em.flush();

        Response createResponse = resource.createContract(contract);
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());

        Response getResponse = resource.getContract(contract.getContractId());
        Assert.assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());
        Contract found = (Contract) getResponse.getEntity();
        Assert.assertEquals(contract.getContractId(), found.getContractId());
    }

    @Test
    public void testGetAllContracts() {
        // Create and persist first contract
        CustomerAddress address1 = new CustomerAddress();
        address1.setCity("City1");
        address1.setPostalCode("12345");
        address1.setStreet("Street1");
        address1.setStreetNumber("1");
        em.persist(address1);
        em.flush();

        Customer customer1 = new Customer();
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setEmail("john@example.com");
        customer1.setBirthdate(new java.util.Date());
        customer1.setFemale(true);
        customer1.setCustomerAddress(address1);
        em.persist(customer1);
        em.flush();

        SaleVehicle saleVehicle1 = new SaleVehicle();
        saleVehicle1.setSalePrice(BigDecimal.valueOf(10000));
        saleVehicle1.setNew(true);
        em.persist(saleVehicle1);
        em.flush();

        Contract contract1 = new Contract();
        contract1.setCustomer(customer1);
        contract1.setSaleVehicle(saleVehicle1);
        contract1.setRentVehicle(null);
        contract1.setRentalContract(false);
        contract1.setContractDate(LocalDate.now());
        contract1.setRentalStartDate(null);
        contract1.setRentalEndDate(null);
        em.persist(contract1);
        em.flush();

        // Create and persist second contract
        CustomerAddress address2 = new CustomerAddress();
        address2.setCity("City2");
        address2.setPostalCode("54321");
        address2.setStreet("Street2");
        address2.setStreetNumber("2");
        em.persist(address2);
        em.flush();

        Customer customer2 = new Customer();
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");
        customer2.setEmail("jane@example.com");
        customer2.setBirthdate(new java.util.Date());
        customer2.setFemale(true);
        customer2.setCustomerAddress(address2);
        em.persist(customer2);
        em.flush();

        SaleVehicle saleVehicle2 = new SaleVehicle();
        saleVehicle2.setSalePrice(BigDecimal.valueOf(20000));
        saleVehicle2.setNew(false);
        em.persist(saleVehicle2);
        em.flush();

        Contract contract2 = new Contract();
        contract2.setCustomer(customer2);
        contract2.setSaleVehicle(saleVehicle2);
        contract2.setRentVehicle(null);
        contract2.setRentalContract(false);
        contract2.setContractDate(LocalDate.now());
        contract2.setRentalStartDate(null);
        contract2.setRentalEndDate(null);
        em.persist(contract2);
        em.flush();

        List<Contract> contracts = resource.getAllContracts();
        Assert.assertEquals(2, contracts.size());
    }

    @Test
    public void testGetSaleContracts() {
        // Create and persist a sale contract
        CustomerAddress address = new CustomerAddress();
        address.setCity("City");
        address.setPostalCode("12345");
        address.setStreet("Street");
        address.setStreetNumber("1");
        em.persist(address);
        em.flush();

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@example.com");
        customer.setBirthdate(new java.util.Date());
        customer.setFemale(true);
        customer.setCustomerAddress(address);
        em.persist(customer);
        em.flush();

        SaleVehicle saleVehicle = new SaleVehicle();
        saleVehicle.setSalePrice(BigDecimal.valueOf(10000));
        saleVehicle.setNew(true);
        em.persist(saleVehicle);
        em.flush();

        Contract contract = new Contract();
        contract.setCustomer(customer);
        contract.setSaleVehicle(saleVehicle);
        contract.setRentVehicle(null);
        contract.setRentalContract(false);
        contract.setContractDate(LocalDate.now());
        contract.setRentalStartDate(null);
        contract.setRentalEndDate(null);
        em.persist(contract);
        em.flush();

        Response response = resource.getSaleContracts();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        List<Contract> contracts = (List<Contract>) response.getEntity();
        Assert.assertEquals(1, contracts.size());
    }

    @Test
    public void testUpdateContract() {
        // Create initial contract
        CustomerAddress address = new CustomerAddress();
        address.setCity("OldCity");
        address.setPostalCode("12345");
        address.setStreet("OldStreet");
        address.setStreetNumber("1");
        em.persist(address);
        em.flush();

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@example.com");
        customer.setBirthdate(new java.util.Date());
        customer.setFemale(true);
        customer.setCustomerAddress(address);
        em.persist(customer);
        em.flush();

        SaleVehicle saleVehicle = new SaleVehicle();
        saleVehicle.setSalePrice(BigDecimal.valueOf(10000));
        saleVehicle.setNew(true);
        em.persist(saleVehicle);
        em.flush();

        Contract contract = new Contract();
        contract.setCustomer(customer);
        contract.setSaleVehicle(saleVehicle);
        contract.setRentVehicle(null);
        contract.setRentalContract(false);
        contract.setContractDate(LocalDate.now());
        contract.setRentalStartDate(null);
        contract.setRentalEndDate(null);
        em.persist(contract);
        em.flush();

        // Create updated contract data
        Contract updatedContract = new Contract();
        updatedContract.setCustomer(customer);
        updatedContract.setSaleVehicle(saleVehicle);
        updatedContract.setRentVehicle(null);
        updatedContract.setRentalContract(true);
        updatedContract.setContractDate(LocalDate.now());
        updatedContract.setRentalStartDate(LocalDate.now());
        updatedContract.setRentalEndDate(LocalDate.now().plusDays(7));

        Response response = resource.updateContract(contract.getContractId(), updatedContract);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Contract found = (Contract) response.getEntity();
        Assert.assertTrue(found.isRentalContract());
        Assert.assertNotNull(found.getRentalStartDate());
        Assert.assertNotNull(found.getRentalEndDate());
    }

    @Test
    public void testDeleteContract() {
        // Create contract to delete
        CustomerAddress address = new CustomerAddress();
        address.setCity("City");
        address.setPostalCode("12345");
        address.setStreet("Street");
        address.setStreetNumber("1");
        em.persist(address);
        em.flush();

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@example.com");
        customer.setBirthdate(new java.util.Date());
        customer.setFemale(true);
        customer.setCustomerAddress(address);
        em.persist(customer);
        em.flush();

        SaleVehicle saleVehicle = new SaleVehicle();
        saleVehicle.setSalePrice(BigDecimal.valueOf(10000));
        saleVehicle.setNew(true);
        em.persist(saleVehicle);
        em.flush();

        Contract contract = new Contract();
        contract.setCustomer(customer);
        contract.setSaleVehicle(saleVehicle);
        contract.setRentVehicle(null);
        contract.setRentalContract(false);
        contract.setContractDate(LocalDate.now());
        contract.setRentalStartDate(null);
        contract.setRentalEndDate(null);
        em.persist(contract);
        em.flush();

        Response response = resource.deleteContract(contract.getContractId());
        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        // Verify contract is deleted
        Response getResponse = resource.getContract(contract.getContractId());
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), getResponse.getStatus());
    }

    @Test
    public void testGetNonExistentContract() {
        Response response = resource.getContract(999);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateNonExistentContract() {
        Contract nonExistentContract = new Contract();
        nonExistentContract.setContractDate(LocalDate.now());

        Response response = resource.updateContract(999, nonExistentContract);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteNonExistentContract() {
        Response response = resource.deleteContract(999);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}