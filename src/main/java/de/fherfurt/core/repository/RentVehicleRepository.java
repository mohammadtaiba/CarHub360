package de.fherfurt.core.repository;

import de.fherfurt.core.entity.RentVehicle;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Provides CRUD operations for RentVehicle entities in the database.
 */
@Stateless
public class RentVehicleRepository {

    @PersistenceContext
    private EntityManager em;

    public RentVehicle findById(int rentVehicleId) {
        return em.find(RentVehicle.class, rentVehicleId);
    }

    public List<RentVehicle> findAll() {
        return em.createQuery("SELECT rv FROM RentVehicle rv", RentVehicle.class).getResultList();
    }

    public void save(RentVehicle rentVehicle) {
        em.persist(rentVehicle);
    }

    public RentVehicle update(RentVehicle rentVehicle) {
        return em.merge(rentVehicle);
    }

    public void delete(int rentVehicleId) {
        RentVehicle existing = findById(rentVehicleId);
        if (existing != null) {
            em.remove(existing);
        }
    }
}
