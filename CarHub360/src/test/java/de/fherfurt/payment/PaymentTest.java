package de.fherfurt.payment;

import de.fherfurt.customer.Customer;
import de.fherfurt.customerAddress.CustomerAddress;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class PaymentTest
{

    private Payment payment_1, payment_2, payment_3;
    private Customer customer;
    private PaymentMethod paymentMethod_1, paymentMethod_2, paymentMethod_3;
    private PaymentStatus paymentStatus_1, paymentStatus_2, paymentStatus_3;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");



    @Before
    public void setUp() throws Exception {
        payment_1 = new Payment();
        payment_2 = new Payment();
        payment_3 = new Payment();

        CustomerAddress address1 = new CustomerAddress(1, "Anytown", "123", "Main Street1", "12345");
        Customer.createCustomer(1, "Mohammad", "Taiba", "mohammadtaiba55@gmail.com", sdf.parse("01/01/1999"), false, address1);
        customer = Customer.getCustomerList().stream().filter(c -> c.getCustomerId() == 1).findFirst().orElse(null);

        CustomerAddress address2 = new CustomerAddress(2, "Anytown", "1234", "Main Street2", "12345");
        Customer.createCustomer(2, "Ahmad", "Sami", "ahmadsami55@gmail.com", sdf.parse("01/01/1992"), false, address2);

        CustomerAddress address3 = new CustomerAddress(3, "Anytown", "12345", "Main Street3", "12345");
        Customer.createCustomer(3, "Julia", "Müller", "juliamueller@gmail.com", sdf.parse("01/01/1995"), true, address3);

        paymentMethod_1 = PaymentMethod.CREDIT_CARD;
        paymentMethod_2 = PaymentMethod.CASH;
        paymentMethod_3 = PaymentMethod.BANK_TRANSFER;
        paymentStatus_1 = PaymentStatus.PENDING;
        paymentStatus_2 = PaymentStatus.COMPLETED;
        paymentStatus_3 = PaymentStatus.COMPLETED;
    }



    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testProcessPaymentSuccess()
    {
        assertTrue(payment_1.processPayment(1, customer, 1, paymentMethod_1, paymentStatus_1, new BigDecimal("100.00")));
        assertTrue(payment_2.processPayment(2, customer, 2, paymentMethod_2, paymentStatus_2, new BigDecimal("1000.00")));
        assertTrue(payment_3.processPayment(3, customer, 3, paymentMethod_3, paymentStatus_3, new BigDecimal("500.00")));
    }

    @Test
    public void testProcessPaymentFailure()
    {
        assertFalse(payment_1.processPayment(0, customer, 1, paymentMethod_1, paymentStatus_1, new BigDecimal("100.00")));
        assertFalse(payment_1.processPayment(-1, customer, 2, paymentMethod_1, paymentStatus_1, new BigDecimal("100.00")));
        assertFalse(payment_1.processPayment(1, null, 1, paymentMethod_1, paymentStatus_1, new BigDecimal("100.00")));
        assertFalse(payment_1.processPayment(1, customer, 1, null, paymentStatus_1, new BigDecimal("100.00")));
        assertFalse(payment_1.processPayment(1, customer, 1, paymentMethod_1, null, new BigDecimal("100.00")));
        assertFalse(payment_1.processPayment(5, customer, 1, paymentMethod_1, paymentStatus_1, null));
        assertFalse(payment_1.processPayment(5, customer, 1, paymentMethod_1, paymentStatus_1, new BigDecimal(0)));
        assertFalse(payment_1.processPayment(5, customer, 1, paymentMethod_1, paymentStatus_1, new BigDecimal(-1)));
    }

    @Test
    public void testGetPaymentDetailsValid()
    {
        payment_1.processPayment(1, customer,1, paymentMethod_1, paymentStatus_1, new BigDecimal("100.00"));
        String details_1 = payment_1.getPaymentDetails(1);
        assertNotNull(details_1);
        assertTrue(details_1.contains("Payment ID: 1"));

        payment_2.processPayment(2, customer, 2,paymentMethod_2, paymentStatus_2, new BigDecimal("1000.00"));
        String details_2 = payment_2.getPaymentDetails(2);
        assertNotNull(details_2);
        assertTrue(details_2.contains("Payment ID: 2"));

        payment_3.processPayment(3, customer, 3, paymentMethod_3, paymentStatus_3, new BigDecimal("500.00"));
        String details_3 = payment_3.getPaymentDetails(3);
        assertNotNull(details_3);
        assertTrue(details_3.contains("Payment ID: 3"));
    }

    @Test
    public void testGetPaymentDetailsInvalid()
    {
        payment_1.processPayment(1, customer, 1, paymentMethod_1, paymentStatus_1, new BigDecimal("100.00"));
        String details = payment_1.getPaymentDetails(999);
        assertTrue(details.contains("Payment ID not found."));
    }
}