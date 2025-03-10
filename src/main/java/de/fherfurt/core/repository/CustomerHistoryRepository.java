package de.fherfurt.core.repository;

import de.fherfurt.core.entity.CustomerHistory;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Provides CRUD operations for CustomerHistory entities in the database.
 */
@Stateless
public class CustomerHistoryRepository {

    @PersistenceContext
    private EntityManager em;

    public CustomerHistory findById(int customerHistoryId) {
        return em.find(CustomerHistory.class, customerHistoryId);
    }

    public List<CustomerHistory> findAll() {
        return em.createQuery("SELECT ch FROM CustomerHistory ch", CustomerHistory.class)
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
