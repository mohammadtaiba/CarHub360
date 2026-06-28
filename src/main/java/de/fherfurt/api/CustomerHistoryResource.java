package de.fherfurt.api;

import de.fherfurt.api.dto.CustomerHistoryRequest;
import de.fherfurt.core.entity.CustomerHistory;
import de.fherfurt.service.CustomerHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customer-history")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Customer History", description = "Customer interactions, purchases, rentals and reviews")
public class CustomerHistoryResource {

    @Inject
    private CustomerHistoryService customerHistoryService;

    @GET
    @Operation(summary = "List customer history records")
    public Response getCustomerHistories(@QueryParam("customerId") Integer customerId) {
        if (customerId != null) {
            return Response.ok(customerHistoryService.findByCustomerId(customerId)).build();
        }
        return Response.ok(customerHistoryService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one customer history record")
    public Response getCustomerHistory(@PathParam("id") int id) {
        CustomerHistory history = customerHistoryService.findById(id);
        if (history == null) {
            return ApiResponses.notFound("Customer history record not found.");
        }
        return Response.ok(history).build();
    }

    @POST
    @Operation(summary = "Create a customer history record")
    public Response createCustomerHistory(@Valid CustomerHistoryRequest request) {
        try {
            CustomerHistory created = customerHistoryService.create(
                    request.getCustomerId(),
                    request.getVehicleId(),
                    request.getCustomerHistoryReview(),
                    request.getDescription(),
                    request.getActionDate(),
                    request.isForRentalCar()
            );
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a customer history record")
    public Response updateCustomerHistory(@PathParam("id") int id, @Valid CustomerHistoryRequest request) {
        try {
            CustomerHistory updated = customerHistoryService.update(
                    id,
                    request.getCustomerId(),
                    request.getVehicleId(),
                    request.getCustomerHistoryReview(),
                    request.getDescription(),
                    request.getActionDate(),
                    request.isForRentalCar()
            );
            if (updated == null) {
                return ApiResponses.notFound("Customer history record not found.");
            }
            return Response.ok(updated).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a customer history record")
    public Response deleteCustomerHistory(@PathParam("id") int id) {
        if (!customerHistoryService.delete(id)) {
            return ApiResponses.notFound("Customer history record not found.");
        }
        return Response.noContent().build();
    }
}
