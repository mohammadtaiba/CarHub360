package de.fherfurt.carhub360.payment;

import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.customer.CustomerEmailUniquenessService;
import de.fherfurt.carhub360.customer.CustomerProfileUpdater;
import de.fherfurt.carhub360.customer.CustomerReferenceService;
import de.fherfurt.carhub360.customer.CustomerRepository;
import de.fherfurt.carhub360.customer.CustomerService;
import de.fherfurt.carhub360.customer.CustomerValidator;
import de.fherfurt.carhub360.customer.address.CustomerAddress;
import de.fherfurt.carhub360.payment.dto.PaymentCreateRequest;
import de.fherfurt.carhub360.payment.dto.PaymentResponse;
import de.fherfurt.carhub360.payment.dto.PaymentUpdateRequest;
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

class PaymentResourceTest {

    private static EntityManagerFactory emf;

    private EntityManager em;
    private CustomerService customerService;
    private PaymentResource paymentResource;

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
        PaymentRepository paymentRepository = new PaymentRepository();
        inject(customerRepository, "em", em);
        inject(paymentRepository, "em", em);

        customerService = new CustomerService();
        inject(customerService, "customerRepository", customerRepository);
        inject(customerService, "customerValidator", new CustomerValidator());
        CustomerEmailUniquenessService customerEmailUniquenessService = new CustomerEmailUniquenessService();
        inject(customerEmailUniquenessService, "customerRepository", customerRepository);
        inject(customerService, "customerEmailUniquenessService", customerEmailUniquenessService);
        inject(customerService, "customerProfileUpdater", new CustomerProfileUpdater());

        CustomerReferenceService customerReferenceService = new CustomerReferenceService();
        inject(customerReferenceService, "customerRepository", customerRepository);

        PaymentService paymentService = new PaymentService();
        inject(paymentService, "paymentRepository", paymentRepository);
        inject(paymentService, "customerReferenceService", customerReferenceService);
        inject(paymentService, "paymentValidator", new PaymentValidator());

        paymentResource = new PaymentResource();
        inject(paymentResource, "paymentService", paymentService);
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    void createListUpdateAndDeletePayment() {
        Customer customer = customerService.create(customer());
        em.flush();

        Response createResponse = paymentResource.createPayment(paymentRequest(customer.getCustomerId()));

        assertEquals(Response.Status.CREATED.getStatusCode(), createResponse.getStatus());
        PaymentResponse created = (PaymentResponse) createResponse.getEntity();
        assertTrue(created.getPaymentId() > 0);
        assertEquals(customer.getCustomerId(), created.getCustomerId());
        assertEquals(PaymentMethod.BANK_TRANSFER, created.getPaymentMethod());

        Response listResponse = paymentResource.getPayments(customer.getCustomerId());
        List<PaymentResponse> payments = (List<PaymentResponse>) listResponse.getEntity();
        assertEquals(1, payments.size());

        PaymentUpdateRequest updateRequest = updatePaymentRequest(customer.getCustomerId());
        Response updateResponse = paymentResource.updatePayment(created.getPaymentId(), updateRequest);
        PaymentResponse updated = (PaymentResponse) updateResponse.getEntity();

        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus());
        assertEquals(PaymentStatus.COMPLETED, updated.getPaymentStatus());
        assertEquals(BigDecimal.valueOf(300), updated.getPaymentAmount());

        Response deleteResponse = paymentResource.deletePayment(created.getPaymentId());

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
    }

    @Test
    void missingPaymentReturnsNotFoundResponses() {
        assertNotFound(paymentResource.getPayment(999), "Payment not found.");
        assertNotFound(paymentResource.updatePayment(999, updatePaymentRequest(999)), "Payment not found.");
        assertNotFound(paymentResource.deletePayment(999), "Payment not found.");
    }

    private PaymentCreateRequest paymentRequest(int customerId) {
        PaymentCreateRequest request = new PaymentCreateRequest();
        request.setCustomerId(customerId);
        request.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
        request.setPaymentStatus(PaymentStatus.PENDING);
        request.setPaymentAmount(BigDecimal.valueOf(250));
        return request;
    }

    private PaymentUpdateRequest updatePaymentRequest(int customerId) {
        PaymentUpdateRequest request = new PaymentUpdateRequest();
        request.setCustomerId(customerId);
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        request.setPaymentStatus(PaymentStatus.COMPLETED);
        request.setPaymentAmount(BigDecimal.valueOf(300));
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
        customer.setEmail("payment@example.com");
        customer.setBirthdate(new Date());
        customer.setFemale(false);
        customer.setCustomerAddress(address);
        return customer;
    }
}
