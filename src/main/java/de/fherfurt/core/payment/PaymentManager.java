package de.fherfurt.core.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import de.fherfurt.core.customer.Customer;
import de.fherfurt.core.customer.CustomerManager;

/**
 * Manages payment records for customers.
 */
public class PaymentManager {
    private static final BigDecimal MINIMUM_PAYMENT_AMOUNT = BigDecimal.ZERO;
    private List<Payment> payments = new ArrayList<>();

    /**
     * Processes a new payment by validating the input parameters and adding it to the payment collection.
     * The payment will not be processed if it already exists or if any parameters are invalid.
     *
     * @param paymentId Unique identifier for the payment
     * @param customerId The ID of the customer making the payment
     * @param paymentMethod The method used for payment
     * @param paymentStatus The current status of the payment
     * @param paymentAmount The amount of the payment
     * @return true if payment is successfully processed, false if validation fails or payment already exists
     */
    public boolean processPayment(int paymentId, int customerId, PaymentMethod paymentMethod,
                                  PaymentStatus paymentStatus, BigDecimal paymentAmount) {
        if (paymentId <= 0 || paymentMethod == null || paymentStatus == null
                || paymentAmount == null || paymentAmount.compareTo(MINIMUM_PAYMENT_AMOUNT) <= 0) {
            return false;
        }

        Optional<Customer> customerOpt = CustomerManager.getCustomerList().stream()
                .filter(c -> c.getCustomerId() == customerId)
                .findFirst();

        if (!customerOpt.isPresent()) {
            return false;
        }

        boolean paymentExists = payments.stream().anyMatch(payment -> payment.getPaymentId() == paymentId);
        if (paymentExists) {
            return false;
        }

        Customer customer = customerOpt.get();
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
