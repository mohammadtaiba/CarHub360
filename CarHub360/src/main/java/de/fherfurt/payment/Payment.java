package de.fherfurt.payment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Payment
{
    /* Attributes */
    private Map<Integer, PaymentDetails> payments = new HashMap<>();

    /* class-Methods */
    /**
     * Processes a payment and adds it to the payment collection if it does not already exist and the parameters are valid.
     * @param paymentId Unique identifier for the payment.
     * @param customer The customer making the payment.
     * @param paymentMethod The method used for payment.
     * @param paymentStatus The current status of the payment (e.g., processed, pending).
     * @param paymentAmount The amount of the payment.
     * @return true if the payment is successfully processed, false if the paymentId already exists, is less than or equal to 0, or any parameters are invalid.
     */
    public boolean processPayment(int paymentId, Customer customer, PaymentMethod paymentMethod,
                                  PaymentStatus paymentStatus, BigDecimal paymentAmount) {
        if (payments.containsKey(paymentId) || paymentId <= 0 || customer == null || paymentMethod == null
                || paymentStatus == null || paymentAmount == null || paymentAmount.compareTo(BigDecimal.ZERO) <= 0)
        {
            return false;
        }

        PaymentDetails details = new PaymentDetails(customer, paymentMethod, paymentStatus, paymentAmount);
        payments.put(paymentId, details);
        return true;
    }

    /**
     * Retrieves the details of a payment in a formatted string.
     * @param paymentId The unique identifier of the payment.
     * @return A string containing the payment details, or a message indicating the payment ID was not found.
     */
    public String getPaymentDetails(int paymentId)
    {
        PaymentDetails details = payments.get(paymentId);
        if (details == null) {
            return "Payment ID not found.";
        }

        return "Payment ID: " + paymentId + "\n" +
                "Customer's First Name: " + details.getCustomer().getFirstName() + "\n" +
                "Customer's Last Name: " + details.getCustomer().getLastName() + "\n" +
                "Payment Method: " + details.getPaymentMethod() + "\n" +
                "Payment Status: " + details.getPaymentStatus() + "\n" +
                "Payment Amount: " + details.getPaymentAmount();
    }

    /* Inner class to hold payment details */
    private static class PaymentDetails {
        private Customer customer;
        private PaymentMethod paymentMethod;
        private PaymentStatus paymentStatus;
        private BigDecimal paymentAmount;

        public PaymentDetails(Customer customer, PaymentMethod paymentMethod, PaymentStatus paymentStatus, BigDecimal paymentAmount)
        {
            this.customer = customer;
            this.paymentMethod = paymentMethod;
            this.paymentStatus = paymentStatus;
            this.paymentAmount = paymentAmount;
        }

        /* Setter & Getter Methods of inner class-attributes */
        public Customer getCustomer()
        {
            return customer;
        }
        public PaymentMethod getPaymentMethod()
        {
            return paymentMethod;
        }
        public PaymentStatus getPaymentStatus()
        {
            return paymentStatus;
        }
        public BigDecimal getPaymentAmount()
        {
            return paymentAmount;
        }
    }

}
