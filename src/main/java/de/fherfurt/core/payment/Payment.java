package de.fherfurt.core.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import de.fherfurt.core.Customer;
/**
 * This class represents a payment made by a customer.
 * It includes attributes such as payment ID, customer details, payment method, payment status, and payment amount.
 */
public class Payment {
    private int paymentId;
    private Customer customer;
    private int customerId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private BigDecimal paymentAmount;
    private List<Payment> payments = new ArrayList<>();
    /**
     * Parameterized constructor to initialize the payment attributes.
     *
     * @param paymentId Unique identifier for the payment
     * @param customer The customer making the payment
     * @param customerId The ID of the customer making the payment
     * @param paymentMethod The method used for payment
     * @param paymentStatus The current status of the payment (e.g., processed, pending)
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

    // Default constructor for initializing payments list
    public Payment() {
    }

    // Getter and setter methods
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
     * Processes a payment and adds it to the payment collection if it does not already exist and the parameters are valid.
     *
     * @param paymentId Unique identifier for the payment
     * @param customer The customer making the payment
     * @param customerId The ID of the customer making the payment
     * @param paymentMethod The method used for payment
     * @param paymentStatus The current status of the payment (e.g., processed, pending)
     * @param paymentAmount The amount of the payment
     * @return true if the payment is successfully processed, false if the paymentId already exists, is less than or equal to 0, or any parameters are invalid
     */
    public boolean processPayment(int paymentId, Customer customer, int customerId, PaymentMethod paymentMethod,
                                  PaymentStatus paymentStatus, BigDecimal paymentAmount) {
        if (paymentId <= 0 || customer == null || paymentMethod == null || paymentStatus == null
                || paymentAmount == null || paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        for (Payment payment : payments) {
            if (payment.getPaymentId() == paymentId) {
                return false;
            }
        }

        Payment payment = new Payment(paymentId, customer, customerId, paymentMethod, paymentStatus, paymentAmount);
        payments.add(payment);
        return true;
    }

    /**
     * Retrieves the details of a payment in a formatted string.
     *
     * @param paymentId The unique identifier of the payment
     * @return A string containing the payment details, or a message indicating the payment ID was not found
     */
    public String getPaymentDetails(int paymentId) {
        for (Payment payment : payments) {
            if (payment.getPaymentId() == paymentId) {
                return "Payment ID: " + payment.getPaymentId() + "\n" +
                        "Customer: " + payment.getCustomer().getFullName() + "\n" + // Assuming getFullName() returns a string with customer details
                        "Payment Method: " + payment.getPaymentMethod() + "\n" +
                        "Payment Status: " + payment.getPaymentStatus() + "\n" +
                        "Payment Amount: " + payment.getPaymentAmount();
            }
        }
        return "Payment ID not found.";
    }
}
