package de.fherfurt.core.repository;

import de.fherfurt.core.entity.SaleVehicle;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class SaleVehicleRepository {

    @PersistenceContext(unitName = "myPU")
    private EntityManager em;

    public SaleVehicle findById(int vehicleId) {
        return em.find(SaleVehicle.class, vehicleId);
    }

    public List<SaleVehicle> findAll() {
        return em.createQuery("SELECT sv FROM SaleVehicle sv ORDER BY sv.vehicleId", SaleVehicle.class)
                .getResultList();
    }

    public void save(SaleVehicle saleVehicle) {
        em.persist(saleVehicle);
    }

    public SaleVehicle update(SaleVehicle saleVehicle) {
        return em.merge(saleVehicle);
    }

    public void delete(int vehicleId) {
        SaleVehicle existing = findById(vehicleId);
        if (existing != null) {
            em.remove(existing);
        }
    }
}
