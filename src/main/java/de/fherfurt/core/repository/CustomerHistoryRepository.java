package de.fherfurt.core.repository;

import de.fherfurt.core.entity.CustomerHistory;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class CustomerHistoryRepository {

    @PersistenceContext(unitName = "myPU")
    private EntityManager em;

    public CustomerHistory findById(int customerHistoryId) {
        return em.find(CustomerHistory.class, customerHistoryId);
    }

    public List<CustomerHistory> findAll() {
        return em.createQuery(
                        "SELECT ch FROM CustomerHistory ch ORDER BY ch.customerHistoryId",
                        CustomerHistory.class
                )
                .getResultList();
    }

    public List<CustomerHistory> findByCustomerId(int customerId) {
        return em.createQuery(
                        "SELECT ch FROM CustomerHistory ch WHERE ch.customer.customerId = :customerId "
                                + "ORDER BY ch.customerHistoryId",
                        CustomerHistory.class
                )
                .setParameter("customerId", customerId)
                .getResultList();
    }

    public void save(CustomerHistory history) {
        em.persist(history);
    }

    public CustomerHistory update(CustomerHistory history) {
        return em.merge(history);
    }

    public void delete(int customerHistoryId) {
        CustomerHistory existing = findById(customerHistoryId);
        if (existing != null) {
            em.remove(existing);
        }
    }
}
