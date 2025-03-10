package de.fherfurt.core.repository;

import de.fherfurt.core.entity.Vehicle;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Manages CRUD operations for Vehicle entities in the database.
 */
@Stateless
public class VehicleRepository {

    @PersistenceContext
    private EntityManager em;

    public Vehicle findById(int vehicleId) {
        return em.find(Vehicle.class, vehicleId);
    }

    public List<Vehicle> findAll() {
        return em.createQuery("SELECT v FROM Vehicle v", Vehicle.class).getResultList();
    }

    public void save(Vehicle vehicle) {
        em.persist(vehicle);
    }

    public Vehicle update(Vehicle vehicle) {
        return em.merge(vehicle);
    }

    public void delete(int vehicleId) {
        Vehicle existing = findById(vehicleId);
        if (existing != null) {
            em.remove(existing);
        }
    }
}
