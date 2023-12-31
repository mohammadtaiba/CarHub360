package de.fherfurt.payment;

import de.fherfurt.Customer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Payment
{
    /* Attributes */
    private Map<Integer, PaymentDetails> payments = new HashMap<>();

    /* class-Methods */
    public boolean processPayment(int paymentId, Customer customer, PaymentMethod paymentMethod,
                                  PaymentStatus paymentStatus, BigDecimal paymentAmount) {
        if (payments.containsKey(paymentId) || paymentId <= 0 || customer == null || paymentMethod == null
                || paymentStatus == null || paymentAmount == null || paymentAmount.compareTo(BigDecimal.ZERO) < 0)
        {
            return false;
        }

        PaymentDetails details = new PaymentDetails(customer, paymentMethod, paymentStatus, paymentAmount);
        payments.put(paymentId, details);
        return true;
    }

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

    // Additional getters or methods if needed
}
