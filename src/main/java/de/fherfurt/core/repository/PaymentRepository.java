package de.fherfurt.core.repository;

import de.fherfurt.core.entity.Payment;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Manages CRUD operations for Payment entities in the database.
 */
@Stateless
public class PaymentRepository {

    @PersistenceContext
    private EntityManager em;

    public Payment findById(int paymentId) {
        return em.find(Payment.class, paymentId);
    }

    public List<Payment> findAll() {
        return em.createQuery("SELECT p FROM Payment p", Payment.class).getResultList();
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

    /**
     * Beispiel: Prüfen, ob Payment mit dieser ID existiert.
     */
    public boolean existsById(int paymentId) {
        return (findById(paymentId) != null);
    }
}
