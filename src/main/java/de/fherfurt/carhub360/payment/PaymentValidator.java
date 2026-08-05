package de.fherfurt.carhub360.payment;

import jakarta.ejb.Stateless;
import java.math.BigDecimal;

@Stateless
public class PaymentValidator {

    public void validate(PaymentMethod method, PaymentStatus status, BigDecimal amount) {
        if (method == null) {
            throw new IllegalArgumentException("paymentMethod is required.");
        }
        if (status == null) {
            throw new IllegalArgumentException("paymentStatus is required.");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("paymentAmount must be greater than zero.");
        }
    }
}
