package de.fherfurt.carhub360.customer;

import de.fherfurt.carhub360.customer.dto.CustomerCreateRequest;
import de.fherfurt.carhub360.customer.dto.CustomerUpdateRequest;
import de.fherfurt.carhub360.shared.api.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Customers", description = "Customer lifecycle and soft-delete operations")
public class CustomerResource {

    @Inject
    private CustomerService customerService;

    @GET
    @Operation(summary = "List active customers")
    public Response getAllCustomers() {
        return Response.ok(CustomerMapper.toResponses(customerService.findAllActive())).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one active customer")
    public Response getCustomer(@PathParam("id") int id) {
        Customer customer = customerService.findActiveById(id);
        if (customer == null) {
            return ApiResponses.notFound("Customer not found.");
        }
        return Response.ok(CustomerMapper.toResponse(customer)).build();
    }

    @POST
    @Operation(summary = "Create a customer")
    public Response createCustomer(@Valid CustomerCreateRequest request) {
        try {
            Customer created = customerService.create(CustomerMapper.toCustomer(request));
            return Response.status(Response.Status.CREATED)
                    .entity(CustomerMapper.toResponse(created))
                    .build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a customer")
    public Response updateCustomer(@PathParam("id") int id, @Valid CustomerUpdateRequest request) {
        try {
            Customer updated = customerService.update(id, CustomerMapper.toCustomer(request));
            if (updated == null) {
                return ApiResponses.notFound("Customer not found.");
            }
            return Response.ok(CustomerMapper.toResponse(updated)).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Soft-delete a customer")
    public Response deleteCustomer(@PathParam("id") int id) {
        if (!customerService.softDelete(id)) {
            return ApiResponses.notFound("Customer not found.");
        }
        return Response.noContent().build();
    }
}
