package de.fherfurt.core.repository;

import de.fherfurt.core.entity.Contract;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Repository (DAO) for Contract entities.
 * Handles all database (CRUD) operations related to Contract.
 */
@Stateless
public class ContractRepository {

    @PersistenceContext
    private EntityManager em;

    public Contract findById(int contractId) {
        return em.find(Contract.class, contractId);
    }

    public List<Contract> findAll() {
        return em.createQuery("SELECT c FROM Contract c", Contract.class).getResultList();
    }

    public void save(Contract contract) {
        em.persist(contract);
    }

    public Contract update(Contract contract) {
        return em.merge(contract);
    }

    public void delete(int contractId) {
        Contract existing = findById(contractId);
        if (existing != null) {
            em.remove(existing);
        }
    }

    /**
     * Prüft, ob ein Contract mit dieser ID existiert.
     */
    public boolean exists(int contractId) {
        return (findById(contractId) != null);
    }
}
