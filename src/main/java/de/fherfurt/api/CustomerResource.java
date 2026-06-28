package de.fherfurt.api;

import de.fherfurt.api.dto.CustomerAddressRequest;
import de.fherfurt.api.dto.CustomerRequest;
import de.fherfurt.core.entity.Customer;
import de.fherfurt.core.entity.CustomerAddress;
import de.fherfurt.service.CustomerService;
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
        return Response.ok(customerService.findAllActive()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one active customer")
    public Response getCustomer(@PathParam("id") int id) {
        Customer customer = customerService.findActiveById(id);
        if (customer == null) {
            return ApiResponses.notFound("Customer not found.");
        }
        return Response.ok(customer).build();
    }

    @POST
    @Operation(summary = "Create a customer")
    public Response createCustomer(@Valid CustomerRequest request) {
        try {
            Customer created = customerService.create(toCustomer(request));
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a customer")
    public Response updateCustomer(@PathParam("id") int id, @Valid CustomerRequest request) {
        try {
            Customer updated = customerService.update(id, toCustomer(request));
            if (updated == null) {
                return ApiResponses.notFound("Customer not found.");
            }
            return Response.ok(updated).build();
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

    private Customer toCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setBirthdate(request.getBirthdate());
        customer.setFemale(Boolean.TRUE.equals(request.getFemale()));
        customer.setCustomerAddress(toAddress(request.getAddress()));
        return customer;
    }

    private CustomerAddress toAddress(CustomerAddressRequest request) {
        if (request == null) {
            return null;
        }
        CustomerAddress address = new CustomerAddress();
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());
        address.setStreet(request.getStreet());
        address.setStreetNumber(request.getStreetNumber());
        return address;
    }
}
