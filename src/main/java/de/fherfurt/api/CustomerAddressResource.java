package de.fherfurt.api;

import de.fherfurt.api.dto.CustomerAddressRequest;
import de.fherfurt.core.entity.CustomerAddress;
import de.fherfurt.service.CustomerAddressService;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
        return Response.ok(customerAddressService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one address")
    public Response getAddress(@PathParam("id") int id) {
        CustomerAddress address = customerAddressService.findById(id);
        if (address == null) {
            return ApiResponses.notFound("Address not found.");
        }
        return Response.ok(address).build();
    }

    @POST
    @Operation(summary = "Create an address")
    public Response createAddress(@Valid CustomerAddressRequest request) {
        try {
            CustomerAddress created = customerAddressService.create(toAddress(request));
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an address")
    public Response updateAddress(@PathParam("id") int id, @Valid CustomerAddressRequest request) {
        try {
            CustomerAddress updated = customerAddressService.update(id, toAddress(request));
            if (updated == null) {
                return ApiResponses.notFound("Address not found.");
            }
            return Response.ok(updated).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete an address")
    public Response deleteAddress(@PathParam("id") int id) {
        if (!customerAddressService.delete(id)) {
            return ApiResponses.notFound("Address not found.");
        }
        return Response.noContent().build();
    }

    private CustomerAddress toAddress(CustomerAddressRequest request) {
        CustomerAddress address = new CustomerAddress();
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());
        address.setStreet(request.getStreet());
        address.setStreetNumber(request.getStreetNumber());
        return address;
    }
}
