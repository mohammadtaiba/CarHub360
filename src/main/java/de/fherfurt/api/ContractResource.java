package de.fherfurt.api;

import de.fherfurt.core.entity.Contract;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/contracts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContractResource {

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    public List<Contract> getAllContracts() {
        return entityManager.createQuery("SELECT c FROM Contract c", Contract.class)
                .getResultList();
    }

    @GET
    @Path("/{id}")
    public Response getContract(@PathParam("id") int id) {
        Contract contract = entityManager.find(Contract.class, id);
        if (contract == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(contract).build();
    }

    @GET
    @Path("/customer/{customerId}")
    public Response getContractsByCustomer(@PathParam("customerId") int customerId) {
        List<Contract> contracts = entityManager.createQuery(
                "SELECT c FROM Contract c WHERE c.customer.customerId = :customerId", Contract.class)
                .setParameter("customerId", customerId)
                .getResultList();
        return Response.ok(contracts).build();
    }

    @GET
    @Path("/rental")
    public Response getRentalContracts() {
        List<Contract> contracts = entityManager.createQuery(
                "SELECT c FROM Contract c WHERE c.isRentalContract = true", Contract.class)
                .getResultList();
        return Response.ok(contracts).build();
    }

    @GET
    @Path("/sale")
    public Response getSaleContracts() {
        List<Contract> contracts = entityManager.createQuery(
                "SELECT c FROM Contract c WHERE c.isRentalContract = false", Contract.class)
                .getResultList();
        return Response.ok(contracts).build();
    }

    @POST
    public Response createContract(Contract contract) {
        entityManager.persist(contract);
        return Response.status(Response.Status.CREATED)
                .entity(contract)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContract(@PathParam("id") int id, Contract updatedContract) {
        Contract existingContract = entityManager.find(Contract.class, id);
        if (existingContract == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        existingContract.setCustomer(updatedContract.getCustomer());
        existingContract.setSaleVehicle(updatedContract.getSaleVehicle());
        existingContract.setRentVehicle(updatedContract.getRentVehicle());
        existingContract.setRentalContract(updatedContract.isRentalContract());
        existingContract.setContractDate(updatedContract.getContractDate());
        existingContract.setRentalStartDate(updatedContract.getRentalStartDate());
        existingContract.setRentalEndDate(updatedContract.getRentalEndDate());

        return Response.ok(existingContract).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContract(@PathParam("id") int id) {
        Contract contract = entityManager.find(Contract.class, id);
        if (contract == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entityManager.remove(contract);
        return Response.noContent().build();
    }
}
