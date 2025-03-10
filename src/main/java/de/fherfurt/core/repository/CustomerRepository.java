package de.fherfurt.core.repository;

import de.fherfurt.core.entity.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Repository (DAO) for Customer entities.
 * Handles CRUD operations on the database.
 */
@Stateless
public class CustomerRepository {

    @PersistenceContext
    private EntityManager em;

    public Customer findById(int customerId) {
        return em.find(Customer.class, customerId);
    }

    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    public void save(Customer customer) {
        em.persist(customer);
    }

    public Customer update(Customer customer) {
        return em.merge(customer);
    }

    public void delete(int customerId) {
        Customer existing = findById(customerId);
        if (existing != null) {
            em.remove(existing);
        }
    }

    /**
     * Prüft, ob es bereits einen Customer mit gleicher ID oder E-Mail gibt.
     * Du kannst auch zwei Methoden existById(...) und existByEmail(...) machen,
     * hier der Einfachheit halber kombiniert.
     */
    public boolean existsByIdOrEmail(int customerId, String email) {
        Long count = em.createQuery(
                        "SELECT COUNT(c) FROM Customer c WHERE c.customerId = :cid OR c.email = :mail",
                        Long.class
                )
                .setParameter("cid", customerId)
                .setParameter("mail", email)
                .getSingleResult();

        return count > 0;
    }
}
