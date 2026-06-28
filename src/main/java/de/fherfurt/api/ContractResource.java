package de.fherfurt.api;

import de.fherfurt.api.dto.ContractRequest;
import de.fherfurt.core.entity.Contract;
import de.fherfurt.service.ContractService;
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

@Path("/contracts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Contracts", description = "Sale and rental contract operations")
public class ContractResource {

    @Inject
    private ContractService contractService;

    @GET
    @Operation(summary = "List contracts")
    public Response getAllContracts() {
        return Response.ok(contractService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one contract")
    public Response getContract(@PathParam("id") int id) {
        Contract contract = contractService.findById(id);
        if (contract == null) {
            return ApiResponses.notFound("Contract not found.");
        }
        return Response.ok(contract).build();
    }

    @GET
    @Path("/customer/{customerId}")
    @Operation(summary = "List contracts for a customer")
    public Response getContractsByCustomer(@PathParam("customerId") int customerId) {
        return Response.ok(contractService.findByCustomerId(customerId)).build();
    }

    @GET
    @Path("/rental")
    @Operation(summary = "List rental contracts")
    public Response getRentalContracts() {
        return Response.ok(contractService.findRentalContracts()).build();
    }

    @GET
    @Path("/sale")
    @Operation(summary = "List sale contracts")
    public Response getSaleContracts() {
        return Response.ok(contractService.findSaleContracts()).build();
    }

    @GET
    @Path("/{id}/rental-price")
    @Operation(summary = "Calculate a rental contract price")
    public Response getRentalPrice(@PathParam("id") int id) {
        if (contractService.findById(id) == null) {
            return ApiResponses.notFound("Contract not found.");
        }
        var price = contractService.calculateRentalPrice(id);
        if (price == null) {
            return ApiResponses.badRequest(new IllegalArgumentException("Contract is not a rental contract."));
        }
        return Response.ok(price).build();
    }

    @POST
    @Operation(summary = "Create a contract")
    public Response createContract(@Valid ContractRequest request) {
        try {
            Contract created = contractService.create(
                    request.getCustomerId(),
                    request.getSaleVehicleId(),
                    request.getRentVehicleId(),
                    request.isRentalContract(),
                    request.getContractDate(),
                    request.getRentalStartDate(),
                    request.getRentalEndDate()
            );
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a contract")
    public Response updateContract(@PathParam("id") int id, @Valid ContractRequest request) {
        try {
            Contract updated = contractService.update(
                    id,
                    request.getCustomerId(),
                    request.getSaleVehicleId(),
                    request.getRentVehicleId(),
                    request.isRentalContract(),
                    request.getContractDate(),
                    request.getRentalStartDate(),
                    request.getRentalEndDate()
            );
            if (updated == null) {
                return ApiResponses.notFound("Contract not found.");
            }
            return Response.ok(updated).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a contract")
    public Response deleteContract(@PathParam("id") int id) {
        if (!contractService.delete(id)) {
            return ApiResponses.notFound("Contract not found.");
        }
        return Response.noContent().build();
    }
}
