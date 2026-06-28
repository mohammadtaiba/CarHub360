package de.fherfurt.core.repository;

import de.fherfurt.core.entity.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class CustomerRepository {

    @PersistenceContext(unitName = "myPU")
    private EntityManager em;

    public Customer findById(int customerId) {
        return em.find(Customer.class, customerId);
    }

    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c ORDER BY c.customerId", Customer.class)
                .getResultList();
    }

    public List<Customer> findAllActive() {
        return em.createQuery(
                        "SELECT c FROM Customer c WHERE c.deleted = false ORDER BY c.customerId",
                        Customer.class
                )
                .getResultList();
    }

    public Customer findByEmail(String email) {
        List<Customer> result = em.createQuery(
                        "SELECT c FROM Customer c WHERE LOWER(c.email) = LOWER(:email)",
                        Customer.class
                )
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
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
}
