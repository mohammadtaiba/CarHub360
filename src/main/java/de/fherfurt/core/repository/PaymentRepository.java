package de.fherfurt.core.repository;

import de.fherfurt.core.entity.Payment;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class PaymentRepository {

    @PersistenceContext(unitName = "myPU")
    private EntityManager em;

    public Payment findById(int paymentId) {
        return em.find(Payment.class, paymentId);
    }

    public List<Payment> findAll() {
        return em.createQuery("SELECT p FROM Payment p ORDER BY p.paymentId", Payment.class)
                .getResultList();
    }

    public List<Payment> findByCustomerId(int customerId) {
        return em.createQuery(
                        "SELECT p FROM Payment p WHERE p.customer.customerId = :customerId ORDER BY p.paymentId",
                        Payment.class
                )
                .setParameter("customerId", customerId)
                .getResultList();
    }

    public void save(Payment payment) {
        em.persist(payment);
    }

    public Payment update(Payment payment) {
        return em.merge(payment);
    }

    public void delete(int paymentId) {
        Payment existing = findById(paymentId);
        if (existing != null) {
            em.remove(existing);
        }
    }
}
