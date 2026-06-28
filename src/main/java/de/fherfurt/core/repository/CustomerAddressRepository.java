package de.fherfurt.core.repository;

import de.fherfurt.core.entity.CustomerAddress;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class CustomerAddressRepository {

    @PersistenceContext(unitName = "myPU")
    private EntityManager em;

    public CustomerAddress findById(int addressId) {
        return em.find(CustomerAddress.class, addressId);
    }

    public List<CustomerAddress> findAll() {
        return em.createQuery("SELECT ca FROM CustomerAddress ca ORDER BY ca.addressId", CustomerAddress.class)
                .getResultList();
    }

    public void save(CustomerAddress address) {
        em.persist(address);
    }

    public CustomerAddress update(CustomerAddress address) {
        return em.merge(address);
    }

    public void delete(int addressId) {
        CustomerAddress existing = findById(addressId);
        if (existing != null) {
            em.remove(existing);
        }
    }
}
