package de.fherfurt.service;

import de.fherfurt.core.entity.Payment;
import de.fherfurt.core.entity.utils.PaymentMethod;
import de.fherfurt.core.entity.utils.PaymentStatus;
import de.fherfurt.core.entity.Customer;
import de.fherfurt.core.repository.PaymentRepository;
import de.fherfurt.core.repository.CustomerRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.math.BigDecimal;

/**
 * Manages payment records for customers, using persistent storage.
 */
@Stateless
public class PaymentService {

    private static final BigDecimal MINIMUM_PAYMENT_AMOUNT = BigDecimal.ZERO;

    @Inject
    private PaymentRepository paymentRepository;

    @Inject
    private CustomerRepository customerRepository;
    // Angenommen, du hast ein CustomerRepository mit findById(int) etc.

    /**
     * Processes a new payment by validating the input parameters and adding it to the DB.
     * The payment will not be processed if it already exists or if any parameters are invalid.
     *
     * @param paymentId Unique identifier for the payment
     * @param customerId The ID of the customer making the payment
     * @param paymentMethod The method used for payment
     * @param paymentStatus The current status of the payment
     * @param paymentAmount The amount of the payment
     * @return true if payment is successfully processed, false if validation fails or payment already exists
     */
    public boolean processPayment(int paymentId,
                                  int customerId,
                                  PaymentMethod paymentMethod,
                                  PaymentStatus paymentStatus,
                                  BigDecimal paymentAmount) {

        // Validierung
        if (paymentId <= 0 || paymentMethod == null || paymentStatus == null
                || paymentAmount == null || paymentAmount.compareTo(MINIMUM_PAYMENT_AMOUNT) <= 0) {
            return false;
        }

        // Customer aus DB laden
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            return false;  // Customer existiert nicht
        }

        // Prüfen, ob Payment bereits existiert
        if (paymentRepository.existsById(paymentId)) {
            return false;
        }

        // Payment anlegen und speichern
        Payment payment = new Payment(
                paymentId,
                customer,
                customerId,
                paymentMethod,
                paymentStatus,
                paymentAmount
        );
        paymentRepository.save(payment);
        return true;
    }

    /**
     * Retrieves formatted payment details for a given payment ID.
     *
     * @param paymentId The unique identifier of the payment
     * @return A formatted string containing payment details, or "Payment ID not found" if not found
     */
    public String getPaymentDetails(int paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment != null) {
            return "Payment ID: " + payment.getPaymentId() + "\n" +
                    "Customer: " + payment.getCustomer().getFullName() + "\n" +
                    "Payment Method: " + payment.getPaymentMethod() + "\n" +
                    "Payment Status: " + payment.getPaymentStatus() + "\n" +
                    "Payment Amount: " + payment.getPaymentAmount();
        }
        return "Payment ID not found.";
    }
}
