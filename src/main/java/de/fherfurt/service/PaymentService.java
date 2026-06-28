package de.fherfurt.service;

import de.fherfurt.core.entity.Customer;
import de.fherfurt.core.entity.Payment;
import de.fherfurt.core.entity.utils.PaymentMethod;
import de.fherfurt.core.entity.utils.PaymentStatus;
import de.fherfurt.core.repository.CustomerRepository;
import de.fherfurt.core.repository.PaymentRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

@Stateless
public class PaymentService {

    @Inject
    private PaymentRepository paymentRepository;

    @Inject
    private CustomerRepository customerRepository;

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
        Customer customer = requireActiveCustomer(customerId);
        validate(paymentMethod, paymentStatus, paymentAmount);
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
        Customer customer = requireActiveCustomer(customerId);
        validate(paymentMethod, paymentStatus, paymentAmount);
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

    private Customer requireActiveCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null || customer.isDeleted()) {
            throw new IllegalArgumentException("customerId does not reference an active customer.");
        }
        return customer;
    }

    private void validate(PaymentMethod method, PaymentStatus status, BigDecimal amount) {
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
