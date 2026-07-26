package de.fherfurt.carhub360.payment;

import de.fherfurt.carhub360.payment.dto.PaymentResponse;
import java.util.List;

final class PaymentMapper {

    private PaymentMapper() {
    }

    static List<PaymentResponse> toResponses(List<Payment> payments) {
        return payments.stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }

    static PaymentResponse toResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getPaymentId());
        response.setCustomerId(payment.getCustomerId());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setPaymentAmount(payment.getPaymentAmount());
        return response;
    }
}
