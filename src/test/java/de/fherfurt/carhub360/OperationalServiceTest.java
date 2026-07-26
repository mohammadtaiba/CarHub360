package de.fherfurt.carhub360;

import de.fherfurt.carhub360.customer.address.CustomerAddress;
import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.customer.CustomerRepository;
import de.fherfurt.carhub360.customer.CustomerService;
import de.fherfurt.carhub360.customer.history.CustomerHistory;
import de.fherfurt.carhub360.customer.history.CustomerHistoryRepository;
import de.fherfurt.carhub360.customer.history.CustomerHistoryReview;
import de.fherfurt.carhub360.customer.history.CustomerHistoryService;
import de.fherfurt.carhub360.maintenance.Maintenance;
import de.fherfurt.carhub360.maintenance.MaintenanceRepository;
import de.fherfurt.carhub360.maintenance.MaintenanceService;
import de.fherfurt.carhub360.payment.Payment;
import de.fherfurt.carhub360.payment.PaymentMethod;
import de.fherfurt.carhub360.payment.PaymentRepository;
import de.fherfurt.carhub360.payment.PaymentService;
import de.fherfurt.carhub360.payment.PaymentStatus;
import de.fherfurt.carhub360.vehicle.Vehicle;
import de.fherfurt.carhub360.vehicle.VehicleRepository;
import de.fherfurt.carhub360.vehicle.VehicleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.fherfurt.carhub360.testsupport.InjectionSupport.inject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OperationalServiceTest {

    private static EntityManagerFactory emf;

    private EntityManager em;
    private CustomerService customerService;
    private VehicleService vehicleService;
    private PaymentService paymentService;
    private MaintenanceService maintenanceService;
    private CustomerHistoryService customerHistoryService;

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
        PaymentRepository paymentRepository = new PaymentRepository();
        MaintenanceRepository maintenanceRepository = new MaintenanceRepository();
        CustomerHistoryRepository historyRepository = new CustomerHistoryRepository();
        inject(customerRepository, "em", em);
        inject(vehicleRepository, "em", em);
        inject(paymentRepository, "em", em);
        inject(maintenanceRepository, "em", em);
        inject(historyRepository, "em", em);

        customerService = new CustomerService();
        inject(customerService, "customerRepository", customerRepository);

        vehicleService = new VehicleService();
        inject(vehicleService, "vehicleRepository", vehicleRepository);

        paymentService = new PaymentService();
        inject(paymentService, "paymentRepository", paymentRepository);
        inject(paymentService, "customerRepository", customerRepository);

        maintenanceService = new MaintenanceService();
        inject(maintenanceService, "maintenanceRepository", maintenanceRepository);
        inject(maintenanceService, "vehicleRepository", vehicleRepository);

        customerHistoryService = new CustomerHistoryService();
        inject(customerHistoryService, "repository", historyRepository);
        inject(customerHistoryService, "customerRepository", customerRepository);
        inject(customerHistoryService, "vehicleRepository", vehicleRepository);
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    void paymentMaintenanceAndHistoryCanBeCreated() {
        Customer customer = customerService.create(customer());
        Vehicle vehicle = vehicleService.create(vehicle());
        em.flush();

        Payment payment = paymentService.create(
                customer.getCustomerId(),
                PaymentMethod.BANK_TRANSFER,
                PaymentStatus.COMPLETED,
                BigDecimal.valueOf(250)
        );
        Maintenance maintenance = maintenanceService.create(
                vehicle.getVehicleId(),
                new Date(),
                null,
                BigDecimal.valueOf(99.99),
                "Oil service"
        );
        CustomerHistory history = customerHistoryService.create(
                customer.getCustomerId(),
                vehicle.getVehicleId(),
                CustomerHistoryReview.FUENF,
                "Successful vehicle handover",
                new Date(),
                false
        );
        em.flush();

        assertTrue(payment.getPaymentId() > 0);
        assertTrue(maintenance.getMaintenanceId() > 0);
        assertTrue(history.getCustomerHistoryId() > 0);
        assertEquals(1, paymentService.findByCustomerId(customer.getCustomerId()).size());
        assertEquals(1, maintenanceService.findByVehicleId(vehicle.getVehicleId()).size());
        assertEquals(1, customerHistoryService.findByCustomerId(customer.getCustomerId()).size());
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
        customer.setEmail("alex@example.com");
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
