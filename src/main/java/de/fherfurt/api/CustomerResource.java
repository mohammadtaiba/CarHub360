package de.fherfurt.api;

import de.fherfurt.core.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Customer Management", description = "APIs for managing customer data")
public class CustomerResource {

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Operation(
        summary = "Get all active customers",
        description = "Retrieves a list of all non-deleted customers",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of customers retrieved successfully",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Customer.class)
                )
            )
        }
    )
    public List<Customer> getAllCustomers() {
        return entityManager.createQuery("SELECT c FROM Customer c WHERE c.isDeleted = false", Customer.class)
                .getResultList();
    }

    @GET
    @Path("/{id}")
    @Operation(
        summary = "Get customer by ID",
        description = "Retrieves a specific customer by their ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Customer found successfully",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Customer.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Customer not found"
            )
        }
    )
    public Response getCustomer(
        @Parameter(description = "ID of the customer to retrieve", required = true)
        @PathParam("id") int id
    ) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer == null || customer.isDeleted()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(customer).build();
    }

    @POST
    @Operation(
        summary = "Create a new customer",
        description = "Creates a new customer record",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Customer created successfully",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Customer.class)
                )
            )
        }
    )
    public Response createCustomer(
        @Parameter(description = "Customer data to create", required = true)
        Customer customer
    ) {
        customer.setDeleted(false);
        entityManager.persist(customer);
        return Response.status(Response.Status.CREATED)
                .entity(customer)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(
        summary = "Update an existing customer",
        description = "Updates the details of an existing customer",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Customer updated successfully",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Customer.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Customer not found"
            )
        }
    )
    public Response updateCustomer(
        @Parameter(description = "ID of the customer to update", required = true)
        @PathParam("id") int id,
        @Parameter(description = "Updated customer data", required = true)
        Customer updatedCustomer
    ) {
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
    @Operation(
        summary = "Delete a customer",
        description = "Soft deletes a customer by setting their deleted flag to true",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Customer deleted successfully"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Customer not found"
            )
        }
    )
    public Response deleteCustomer(
        @Parameter(description = "ID of the customer to delete", required = true)
        @PathParam("id") int id
    ) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer == null || customer.isDeleted()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        customer.setDeleted(true);
        return Response.noContent().build();
    }
}
