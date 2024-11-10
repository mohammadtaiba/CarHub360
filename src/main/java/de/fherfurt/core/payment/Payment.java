package de.fherfurt.core.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import de.fherfurt.core.Customer;

/**
 * Represents a payment made by a customer, tracking payment details like ID, customer info,
 * payment method, status and amount.
 */
public class Payment {
    private static final BigDecimal MINIMUM_PAYMENT_AMOUNT = BigDecimal.ZERO;
    private int paymentId;
    private Customer customer;
    private int customerId; 
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private BigDecimal paymentAmount;
    private List<Payment> payments = new ArrayList<>();

    /**
     * Creates a new Payment with the specified details.
     *
     * @param paymentId Unique identifier for the payment
     * @param customer The customer making the payment
     * @param customerId The ID of the customer making the payment
     * @param paymentMethod The method used for payment
     * @param paymentStatus The current status of the payment
     * @param paymentAmount The amount of the payment
     */
    public Payment(int paymentId, Customer customer, int customerId, PaymentMethod paymentMethod,
                   PaymentStatus paymentStatus, BigDecimal paymentAmount) {
        this.paymentId = paymentId;
        this.customer = customer;
        this.customerId = customerId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentAmount = paymentAmount;
    }

    public Payment() {
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * Processes a new payment by validating the input parameters and adding it to the payment collection.
     * The payment will not be processed if it already exists or if any parameters are invalid.
     *
     * @param paymentId Unique identifier for the payment
     * @param customer The customer making the payment
     * @param customerId The ID of the customer making the payment
     * @param paymentMethod The method used for payment
     * @param paymentStatus The current status of the payment
     * @param paymentAmount The amount of the payment
     * @return true if payment is successfully processed, false if validation fails or payment already exists
     */
    public boolean processPayment(int paymentId, Customer customer, int customerId, PaymentMethod paymentMethod,
                                  PaymentStatus paymentStatus, BigDecimal paymentAmount) {
        if (paymentId <= 0 || customer == null || paymentMethod == null || paymentStatus == null
                || paymentAmount == null || paymentAmount.compareTo(MINIMUM_PAYMENT_AMOUNT) <= 0) {
            return false;
        }

        boolean paymentExists = payments.stream().anyMatch(payment -> payment.getPaymentId() == paymentId);
        if (paymentExists) {
            return false;
        }

        Payment payment = new Payment(paymentId, customer, customerId, paymentMethod, paymentStatus, paymentAmount);
        payments.add(payment);
        return true;
    }

    /**
     * Retrieves formatted payment details for a given payment ID.
     *
     * @param paymentId The unique identifier of the payment
     * @return A formatted string containing payment details, or "Payment ID not found" if not found
     */
    public String getPaymentDetails(int paymentId) {
        Optional<Payment> payment = payments.stream()
                .filter(p -> p.getPaymentId() == paymentId)
                .findFirst();

        return payment.map(p -> "Payment ID: " + p.getPaymentId() + "\n" +
                "Customer: " + p.getCustomer().getFullName() + "\n" +
                "Payment Method: " + p.getPaymentMethod() + "\n" +
                "Payment Status: " + p.getPaymentStatus() + "\n" +
                "Payment Amount: " + p.getPaymentAmount())
                .orElse("Payment ID not found.");
    }
}
