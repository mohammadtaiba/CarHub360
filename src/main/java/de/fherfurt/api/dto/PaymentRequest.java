package de.fherfurt.api.dto;

import de.fherfurt.core.entity.utils.PaymentMethod;
import de.fherfurt.core.entity.utils.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class PaymentRequest {

    @Positive
    private int customerId;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private PaymentStatus paymentStatus;

    @NotNull
    @Positive
    private BigDecimal paymentAmount;

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
}
