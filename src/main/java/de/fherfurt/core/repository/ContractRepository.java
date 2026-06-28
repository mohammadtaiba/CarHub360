package de.fherfurt.core.repository;

import de.fherfurt.core.entity.Contract;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ContractRepository {

    @PersistenceContext(unitName = "myPU")
    private EntityManager em;

    public Contract findById(int contractId) {
        return em.find(Contract.class, contractId);
    }

    public List<Contract> findAll() {
        return em.createQuery("SELECT c FROM Contract c ORDER BY c.contractId", Contract.class)
                .getResultList();
    }

    public List<Contract> findByCustomerId(int customerId) {
        return em.createQuery(
                        "SELECT c FROM Contract c WHERE c.customer.customerId = :customerId ORDER BY c.contractId",
                        Contract.class
                )
                .setParameter("customerId", customerId)
                .getResultList();
    }

    public List<Contract> findRentalContracts() {
        return em.createQuery(
                        "SELECT c FROM Contract c WHERE c.rentalContract = true ORDER BY c.contractId",
                        Contract.class
                )
                .getResultList();
    }

    public List<Contract> findSaleContracts() {
        return em.createQuery(
                        "SELECT c FROM Contract c WHERE c.rentalContract = false ORDER BY c.contractId",
                        Contract.class
                )
                .getResultList();
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
}
