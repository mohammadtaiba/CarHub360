package de.fherfurt.core.repository;

import de.fherfurt.core.entity.Maintenance;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class MaintenanceRepository {

    @PersistenceContext(unitName = "myPU")
    private EntityManager em;

    public Maintenance findById(int maintenanceId) {
        return em.find(Maintenance.class, maintenanceId);
    }

    public List<Maintenance> findAll() {
        return em.createQuery("SELECT m FROM Maintenance m ORDER BY m.maintenanceId", Maintenance.class)
                .getResultList();
    }

    public List<Maintenance> findByVehicleId(int vehicleId) {
        return em.createQuery(
                        "SELECT m FROM Maintenance m WHERE m.vehicle.vehicleId = :vehicleId ORDER BY m.maintenanceId",
                        Maintenance.class
                )
                .setParameter("vehicleId", vehicleId)
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
