package de.fherfurt.api;

import de.fherfurt.core.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    public List<Customer> getAllCustomers() {
        return entityManager.createQuery("SELECT c FROM Customer c WHERE c.isDeleted = false", Customer.class)
                .getResultList();
    }

    @GET
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") int id) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer == null || customer.isDeleted()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(customer).build();
    }

    @POST
    public Response createCustomer(Customer customer) {
        customer.setDeleted(false);
        entityManager.persist(customer);
        return Response.status(Response.Status.CREATED)
                .entity(customer)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        Customer existingCustomer = entityManager.find(Customer.class, id);
        if (existingCustomer == null || existingCustomer.isDeleted()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        existingCustomer.setFirstName(updatedCustomer.getFirstName());
        existingCustomer.setLastName(updatedCustomer.getLastName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setBirthdate(updatedCustomer.getBirthdate());
        existingCustomer.setFemale(updatedCustomer.isFemale());
        existingCustomer.setCustomerAddress(updatedCustomer.getCustomerAddress());

        return Response.ok(existingCustomer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer == null || customer.isDeleted()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        customer.setDeleted(true);
        return Response.noContent().build();
    }
}
