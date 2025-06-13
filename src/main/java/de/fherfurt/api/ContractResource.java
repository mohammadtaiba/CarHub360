package de.fherfurt.api;

import de.fherfurt.core.entity.Contract;
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

@Path("/contracts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Contract Management", description = "APIs for managing vehicle rental and sale contracts")
public class ContractResource {

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Operation(
        summary = "Get all contracts",
        description = "Retrieves a list of all contracts in the system",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of contracts retrieved successfully",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Contract.class)
                )
            )
        }
    )
    public List<Contract> getAllContracts() {
        return entityManager.createQuery("SELECT c FROM Contract c", Contract.class)
                .getResultList();
    }

    @GET
    @Path("/{id}")
    @Operation(
        summary = "Get contract by ID",
        description = "Retrieves a specific contract by its ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Contract found successfully",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Contract.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Contract not found"
            )
        }
    )
    public Response getContract(
        @Parameter(description = "ID of the contract to retrieve", required = true)
        @PathParam("id") int id
    ) {
        Contract contract = entityManager.find(Contract.class, id);
        if (contract == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(contract).build();
    }

    @GET
    @Path("/customer/{customerId}")
    @Operation(
        summary = "Get contracts by customer ID",
        description = "Retrieves all contracts associated with a specific customer",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of customer contracts retrieved successfully",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Contract.class)
                )
            )
        }
    )
    public Response getContractsByCustomer(
        @Parameter(description = "ID of the customer whose contracts to retrieve", required = true)
        @PathParam("customerId") int customerId
    ) {
        List<Contract> contracts = entityManager.createQuery(
                "SELECT c FROM Contract c WHERE c.customer.customerId = :customerId", Contract.class)
                .setParameter("customerId", customerId)
                .getResultList();
        return Response.ok(contracts).build();
    }

    @GET
    @Path("/rental")
    @Operation(
        summary = "Get all rental contracts",
        description = "Retrieves all contracts that are rental contracts",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of rental contracts retrieved successfully",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Contract.class)
                )
            )
        }
    )
    public Response getRentalContracts() {
        List<Contract> contracts = entityManager.createQuery(
                "SELECT c FROM Contract c WHERE c.isRentalContract = true", Contract.class)
                .getResultList();
        return Response.ok(contracts).build();
    }

    @GET
    @Path("/sale")
    @Operation(
        summary = "Get all sale contracts",
        description = "Retrieves all contracts that are sale contracts",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of sale contracts retrieved successfully",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Contract.class)
                )
            )
        }
    )
    public Response getSaleContracts() {
        List<Contract> contracts = entityManager.createQuery(
                "SELECT c FROM Contract c WHERE c.isRentalContract = false", Contract.class)
                .getResultList();
        return Response.ok(contracts).build();
    }

    @POST
    @Operation(
        summary = "Create a new contract",
        description = "Creates a new contract record",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Contract created successfully",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Contract.class)
                )
            )
        }
    )
    public Response createContract(
        @Parameter(description = "Contract data to create", required = true)
        Contract contract
    ) {
        entityManager.persist(contract);
        return Response.status(Response.Status.CREATED)
                .entity(contract)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(
        summary = "Update an existing contract",
        description = "Updates the details of an existing contract",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Contract updated successfully",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Contract.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Contract not found"
            )
        }
    )
    public Response updateContract(
        @Parameter(description = "ID of the contract to update", required = true)
        @PathParam("id") int id,
        @Parameter(description = "Updated contract data", required = true)
        Contract updatedContract
    ) {
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
    @Operation(
        summary = "Delete a contract",
        description = "Permanently deletes a contract from the system",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Contract deleted successfully"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Contract not found"
            )
        }
    )
    public Response deleteContract(
        @Parameter(description = "ID of the contract to delete", required = true)
        @PathParam("id") int id
    ) {
        Contract contract = entityManager.find(Contract.class, id);
        if (contract == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entityManager.remove(contract);
        return Response.noContent().build();
    }
}
