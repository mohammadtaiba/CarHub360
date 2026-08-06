package de.fherfurt.carhub360.payment;

import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.customer.CustomerReferenceService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

@Stateless
public class PaymentService {

    @Inject
    private PaymentRepository paymentRepository;

    @Inject
    private CustomerReferenceService customerReferenceService;

    @Inject
    private PaymentValidator paymentValidator;

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public List<Payment> findByCustomerId(int customerId) {
        return paymentRepository.findByCustomerId(customerId);
    }

    public Payment findById(int paymentId) {
        return paymentRepository.findById(paymentId);
    }

    public Payment create(int customerId,
                          PaymentMethod paymentMethod,
                          PaymentStatus paymentStatus,
                          BigDecimal paymentAmount) {
        Customer customer = customerReferenceService.requireActiveCustomer(customerId);
        paymentValidator.validate(paymentMethod, paymentStatus, paymentAmount);
        Payment payment = new Payment(0, customer, paymentMethod, paymentStatus, paymentAmount);
        paymentRepository.save(payment);
        return payment;
    }

    public Payment update(int paymentId,
                          int customerId,
                          PaymentMethod paymentMethod,
                          PaymentStatus paymentStatus,
                          BigDecimal paymentAmount) {
        Payment existing = paymentRepository.findById(paymentId);
        if (existing == null) {
            return null;
        }
        Customer customer = customerReferenceService.requireActiveCustomer(customerId);
        paymentValidator.validate(paymentMethod, paymentStatus, paymentAmount);
        existing.setCustomer(customer);
        existing.setPaymentMethod(paymentMethod);
        existing.setPaymentStatus(paymentStatus);
        existing.setPaymentAmount(paymentAmount);
        return paymentRepository.update(existing);
    }

    public boolean delete(int paymentId) {
        if (paymentRepository.findById(paymentId) == null) {
            return false;
        }
        paymentRepository.delete(paymentId);
        return true;
    }
}
