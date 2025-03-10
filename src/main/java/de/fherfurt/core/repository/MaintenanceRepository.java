package de.fherfurt.core.repository;

import de.fherfurt.core.entity.Maintenance;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Manages CRUD operations for Maintenance entities in the database.
 */
@Stateless
public class MaintenanceRepository {

    @PersistenceContext
    private EntityManager em;

    public Maintenance findById(int maintenanceId) {
        return em.find(Maintenance.class, maintenanceId);
    }

    public List<Maintenance> findAll() {
        return em.createQuery("SELECT m FROM Maintenance m", Maintenance.class)
                .getResultList();
    }

    public void save(Maintenance maintenance) {
        em.persist(maintenance);
    }

    public Maintenance update(Maintenance maintenance) {
        return em.merge(maintenance);
    }

    public void delete(int maintenanceId) {
        Maintenance existing = findById(maintenanceId);
        if (existing != null) {
            em.remove(existing);
        }
    }
}
