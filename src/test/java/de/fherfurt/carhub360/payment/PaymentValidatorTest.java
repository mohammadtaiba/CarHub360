package de.fherfurt.carhub360.payment;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentValidatorTest {

    private final PaymentValidator validator = new PaymentValidator();

    @Test
    void validateAcceptsValidPaymentData() {
        assertDoesNotThrow(() -> validator.validate(
                PaymentMethod.BANK_TRANSFER,
                PaymentStatus.PENDING,
                BigDecimal.valueOf(250)
        ));
    }

    @Test
    void validateRejectsMissingPaymentMethod() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(null, PaymentStatus.PENDING, BigDecimal.valueOf(250))
        );

        assertEquals("paymentMethod is required.", exception.getMessage());
    }

    @Test
    void validateRejectsMissingPaymentStatus() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(PaymentMethod.BANK_TRANSFER, null, BigDecimal.valueOf(250))
        );

        assertEquals("paymentStatus is required.", exception.getMessage());
    }

    @Test
    void validateRejectsNonPositivePaymentAmount() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validate(PaymentMethod.BANK_TRANSFER, PaymentStatus.PENDING, BigDecimal.ZERO)
        );

        assertEquals("paymentAmount must be greater than zero.", exception.getMessage());
    }
}
