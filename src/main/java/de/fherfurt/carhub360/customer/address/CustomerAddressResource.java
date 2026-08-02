package de.fherfurt.carhub360.customer.address;

import de.fherfurt.carhub360.customer.address.dto.CustomerAddressCreateRequest;
import de.fherfurt.carhub360.customer.address.dto.CustomerAddressUpdateRequest;
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

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Addresses", description = "Reusable customer addresses")
public class CustomerAddressResource {

    @Inject
    private CustomerAddressService customerAddressService;

    @GET
    @Operation(summary = "List addresses")
    public Response getAddresses() {
        return Response.ok(CustomerAddressMapper.toResponses(customerAddressService.findAll())).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one address")
    public Response getAddress(@PathParam("id") int id) {
        return ApiResponses.okOrNotFound(
                customerAddressService.findById(id),
                CustomerAddressMapper::toResponse,
                "Address not found."
        );
    }

    @POST
    @Operation(summary = "Create an address")
    public Response createAddress(@Valid CustomerAddressCreateRequest request) {
        try {
            CustomerAddress created = customerAddressService.create(CustomerAddressMapper.toAddress(request));
            return ApiResponses.created(CustomerAddressMapper.toResponse(created));
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an address")
    public Response updateAddress(@PathParam("id") int id, @Valid CustomerAddressUpdateRequest request) {
        try {
            CustomerAddress updated = customerAddressService.update(id, CustomerAddressMapper.toAddress(request));
            return ApiResponses.okOrNotFound(updated, CustomerAddressMapper::toResponse, "Address not found.");
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete an address")
    public Response deleteAddress(@PathParam("id") int id) {
        return ApiResponses.noContentOrNotFound(customerAddressService.delete(id), "Address not found.");
    }
}
