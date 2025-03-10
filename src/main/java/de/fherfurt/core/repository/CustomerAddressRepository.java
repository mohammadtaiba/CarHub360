package de.fherfurt.core.repository;

import de.fherfurt.core.entity.CustomerAddress;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Provides CRUD operations for CustomerAddress entities in the database.
 */
@Stateless
public class CustomerAddressRepository {

    @PersistenceContext
    private EntityManager em;

    public CustomerAddress findById(int customerId) {
        return em.find(CustomerAddress.class, customerId);
    }

    public List<CustomerAddress> findAll() {
        return em.createQuery("SELECT ca FROM CustomerAddress ca", CustomerAddress.class).getResultList();
    }

    public void save(CustomerAddress address) {
        em.persist(address);
    }

    public CustomerAddress update(CustomerAddress address) {
        return em.merge(address);
    }

    public void delete(int customerId) {
        CustomerAddress existing = findById(customerId);
        if (existing != null) {
            em.remove(existing);
        }
    }
}
