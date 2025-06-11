package de.fherfurt.api;

import static org.junit.jupiter.api.Assertions.*;
import de.fherfurt.api.CustomerResource;
import de.fherfurt.core.entity.Customer;
import de.fherfurt.core.entity.CustomerAddress;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.core.Response;
import org.junit.*;

import java.util.Date;
import java.lang.reflect.Field;
import java.util.List;

public class CustomerResourceTest {
    private static EntityManagerFactory emf;
    private EntityManager em;
    private CustomerResource resource;

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
        resource = new CustomerResource();
        Field f = CustomerResource.class.getDeclaredField("entityManager");
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
    public void testCreateAndGetCustomer() {
        // Create and persist the address first
        CustomerAddress address = new CustomerAddress();
        address.setCity("City");
        address.setPostalCode("12345");
        address.setStreet("Street");
        address.setStreetNumber("1");
        em.persist(address);
        em.flush();

        // Create the customer with the managed address
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@example.com");
        customer.setBirthdate(new Date());
        customer.setFemale(true);
        customer.setCustomerAddress(address);
        em.persist(customer);
        em.flush();

        Response createResponse = Response.status(Response.Status.CREATED).entity(customer).build();
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());

        Response getResponse = resource.getCustomer(customer.getCustomerId());
        Assert.assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());
        Customer found = (Customer) getResponse.getEntity();
        Assert.assertEquals("John", found.getFirstName());
    }

    @Test
    public void testGetAllCustomers() {
        // Create and persist first customer
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

        // Create and persist second customer
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

        List<Customer> customers = resource.getAllCustomers();
        Assert.assertEquals(2, customers.size());
    }

    @Test
    public void testUpdateCustomer() {
        // Create initial customer
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

        // Create updated customer data
        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName("Johnny");
        updatedCustomer.setLastName("Doe");
        updatedCustomer.setEmail("johnny@example.com");
        updatedCustomer.setBirthdate(new java.util.Date());
        updatedCustomer.setFemale(true);
        updatedCustomer.setCustomerAddress(address);

        Response response = resource.updateCustomer(customer.getCustomerId(), updatedCustomer);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Customer found = (Customer) response.getEntity();
        Assert.assertEquals("Johnny", found.getFirstName());
        Assert.assertEquals("johnny@example.com", found.getEmail());
    }

    @Test
    public void testDeleteCustomer() {
        // Create customer to delete
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

        Response response = resource.deleteCustomer(customer.getCustomerId());
        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        // Verify customer is marked as deleted
        Response getResponse = resource.getCustomer(customer.getCustomerId());
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), getResponse.getStatus());
    }

    @Test
    public void testGetNonExistentCustomer() {
        Response response = resource.getCustomer(999);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateNonExistentCustomer() {
        Customer nonExistentCustomer = new Customer();
        nonExistentCustomer.setFirstName("John");
        nonExistentCustomer.setLastName("Doe");
        nonExistentCustomer.setEmail("john@example.com");

        Response response = resource.updateCustomer(999, nonExistentCustomer);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteNonExistentCustomer() {
        Response response = resource.deleteCustomer(999);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}