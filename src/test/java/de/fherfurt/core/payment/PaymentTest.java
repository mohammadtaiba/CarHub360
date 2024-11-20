package de.fherfurt.core.payment;

import de.fherfurt.core.customer.*;
import de.fherfurt.core.payment.*;
import de.fherfurt.logic.customerAddress.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class PaymentTest {

    private Payment payment_1, payment_2, payment_3;
    private Customer customer_1, customer_2, customer_3;
    private PaymentMethod paymentMethod_1, paymentMethod_2, paymentMethod_3;
    private PaymentStatus paymentStatus_1, paymentStatus_2, paymentStatus_3;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private PaymentManager paymentManager_1, paymentManager_2, paymentManager_3;

    @Before
    public void setUp() throws Exception {
        paymentManager_1 = new PaymentManager();
        paymentManager_2 = new PaymentManager();
        paymentManager_3 = new PaymentManager();

        payment_1 = new Payment();
        payment_2 = new Payment();
        payment_3 = new Payment();

        CustomerAddress address1 = new CustomerAddress(123, "Erfurt", "99084", "Main Street", "123");
        CustomerManager.createCustomer(1, "John", "Doe", "john.doe@example.com",
                sdf.parse("02/02/1990"), false, address1);
        customer_1 = CustomerManager.getCustomerList().stream().filter(c -> c.getCustomerId() == 1).findFirst().orElse(null);

        CustomerAddress address2 = new CustomerAddress(2, "Anytown", "1234", "Main Street2", "12345");
        CustomerManager.createCustomer(2, "Ahmad", "Sami", "ahmadsami55@gmail.com", sdf.parse("01/01/1992"), false, address2);
        customer_2 = CustomerManager.getCustomerList().stream().filter(c -> c.getCustomerId() == 2).findFirst().orElse(null);

        CustomerAddress address3 = new CustomerAddress(3, "Anytown", "12345", "Main Street3", "12345");
        CustomerManager.createCustomer(3, "Julia", "Müller", "juliamueller@gmail.com", sdf.parse("01/01/1995"), true, address3);
        customer_3 = CustomerManager.getCustomerList().stream().filter(c -> c.getCustomerId() == 3).findFirst().orElse(null);

        paymentMethod_1 = PaymentMethod.CREDIT_CARD;
        paymentMethod_2 = PaymentMethod.CASH;
        paymentMethod_3 = PaymentMethod.BANK_TRANSFER;
        paymentStatus_1 = PaymentStatus.PENDING;
        paymentStatus_2 = PaymentStatus.COMPLETED;
        paymentStatus_3 = PaymentStatus.COMPLETED;
    }

    @After
    public void tearDown() throws Exception {
        payment_1 = null;
        payment_2 = null;
        payment_3 = null;
        customer_1 = null;
        customer_2 = null;
        customer_3 = null;
    }

    @Test
    public void testProcessPaymentSuccess() {
        assertTrue(paymentManager_1.processPayment(1, customer_1.getCustomerId(), paymentMethod_1, paymentStatus_1, new BigDecimal("100.00")));
        assertTrue(paymentManager_2.processPayment(2, customer_2.getCustomerId(), paymentMethod_2, paymentStatus_2, new BigDecimal("1000.00")));
        assertTrue(paymentManager_3.processPayment(3, customer_3.getCustomerId(), paymentMethod_3, paymentStatus_3, new BigDecimal("500.00")));
    }

    @Test
    public void testProcessPaymentFailure() {
        assertFalse(paymentManager_1.processPayment(0, customer_1.getCustomerId(), paymentMethod_1, paymentStatus_1, new BigDecimal("100.00")));
        assertFalse(paymentManager_1.processPayment(-1, customer_1.getCustomerId(), paymentMethod_1, paymentStatus_1, new BigDecimal("100.00")));
        assertFalse(paymentManager_1.processPayment(1, -1, paymentMethod_1, paymentStatus_1, new BigDecimal("100.00")));
        assertFalse(paymentManager_1.processPayment(1, customer_1.getCustomerId(), null, paymentStatus_1, new BigDecimal("100.00")));
        assertFalse(paymentManager_1.processPayment(1, customer_1.getCustomerId(), paymentMethod_1, null, new BigDecimal("100.00")));
        assertFalse(paymentManager_1.processPayment(5, customer_1.getCustomerId(), paymentMethod_1, paymentStatus_1, null));
        assertFalse(paymentManager_1.processPayment(5, customer_1.getCustomerId(), paymentMethod_1, paymentStatus_1, new BigDecimal(0)));
        assertFalse(paymentManager_1.processPayment(5, customer_1.getCustomerId(), paymentMethod_1, paymentStatus_1, new BigDecimal(-1)));
        paymentManager_1.processPayment(1, customer_1.getCustomerId(), paymentMethod_1, paymentStatus_1, new BigDecimal("100.00"));

        assertFalse(paymentManager_1.processPayment(1, customer_2.getCustomerId(), paymentMethod_2, paymentStatus_2, new BigDecimal("200.00")));
    }

    @Test
    public void testGetPaymentDetailsValid() {
        paymentManager_1.processPayment(1, customer_1.getCustomerId(), paymentMethod_1, paymentStatus_1, new BigDecimal("100.00"));
        String details_1 = paymentManager_1.getPaymentDetails(1);
        assertNotNull(details_1);
        assertTrue(details_1.contains("Payment ID: 1"));

        paymentManager_2.processPayment(2, customer_2.getCustomerId(), paymentMethod_2, paymentStatus_2, new BigDecimal("1000.00"));
        String details_2 = paymentManager_2.getPaymentDetails(2);
        assertNotNull(details_2);
        assertTrue(details_2.contains("Payment ID: 2"));

        paymentManager_3.processPayment(3, customer_3.getCustomerId(), paymentMethod_3, paymentStatus_3, new BigDecimal("500.00"));
        String details_3 = paymentManager_3.getPaymentDetails(3);
        assertNotNull(details_3);
        assertTrue(details_3.contains("Payment ID: 3"));
    }

    @Test
    public void testGetPaymentDetailsInvalid() {
        paymentManager_1.processPayment(1, customer_1.getCustomerId(), paymentMethod_1, paymentStatus_1, new BigDecimal("100.00"));
        String details = paymentManager_1.getPaymentDetails(999);
        assertTrue(details.contains("Payment ID not found."));
    }

    @Test
    public void testDefaultConstructor() {
        Payment payment = new Payment();
        assertNotNull(payment);
    }

    @Test
    public void testSettersAndGetters() {
        Payment payment = new Payment();
        payment.setPaymentId(1);
        payment.setCustomer(customer_1);
        payment.setCustomerId(1);
        payment.setPaymentMethod(paymentMethod_1);
        payment.setPaymentStatus(paymentStatus_1);
        payment.setPaymentAmount(new BigDecimal("100.00"));

        assertEquals(1, payment.getPaymentId());
        assertEquals(customer_1, payment.getCustomer());
        assertEquals(1, payment.getCustomerId());
        assertEquals(paymentMethod_1, payment.getPaymentMethod());
        assertEquals(paymentStatus_1, payment.getPaymentStatus());
        assertEquals(new BigDecimal("100.00"), payment.getPaymentAmount());
    }

    @Test
    public void testProcessPaymentWithLargeAmount() {
        assertTrue(paymentManager_1.processPayment(4, customer_1.getCustomerId(), paymentMethod_1, paymentStatus_1, new BigDecimal("1000000.00")));
        String details = paymentManager_1.getPaymentDetails(4);
        assertNotNull(details);
        assertTrue(details.contains("Payment Amount: 1000000.00"));
    }
}

