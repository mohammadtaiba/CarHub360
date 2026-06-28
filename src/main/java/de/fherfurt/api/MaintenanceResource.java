package de.fherfurt.api;

import de.fherfurt.api.dto.MaintenanceRequest;
import de.fherfurt.core.entity.Maintenance;
import de.fherfurt.service.MaintenanceService;
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

@Path("/maintenance")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Maintenance", description = "Vehicle maintenance records")
public class MaintenanceResource {

    @Inject
    private MaintenanceService maintenanceService;

    @GET
    @Operation(summary = "List maintenance records")
    public Response getMaintenanceRecords(@QueryParam("vehicleId") Integer vehicleId) {
        if (vehicleId != null) {
            return Response.ok(maintenanceService.findByVehicleId(vehicleId)).build();
        }
        return Response.ok(maintenanceService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one maintenance record")
    public Response getMaintenance(@PathParam("id") int id) {
        Maintenance maintenance = maintenanceService.findById(id);
        if (maintenance == null) {
            return ApiResponses.notFound("Maintenance record not found.");
        }
        return Response.ok(maintenance).build();
    }

    @POST
    @Operation(summary = "Create a maintenance record")
    public Response createMaintenance(@Valid MaintenanceRequest request) {
        try {
            Maintenance created = maintenanceService.create(
                    request.getVehicleId(),
                    request.getMaintenanceStartDate(),
                    request.getMaintenanceEndDate(),
                    request.getMaintenanceCost(),
                    request.getMaintenanceDescription()
            );
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a maintenance record")
    public Response updateMaintenance(@PathParam("id") int id, @Valid MaintenanceRequest request) {
        try {
            Maintenance updated = maintenanceService.update(
                    id,
                    request.getVehicleId(),
                    request.getMaintenanceStartDate(),
                    request.getMaintenanceEndDate(),
                    request.getMaintenanceCost(),
                    request.getMaintenanceDescription()
            );
            if (updated == null) {
                return ApiResponses.notFound("Maintenance record not found.");
            }
            return Response.ok(updated).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a maintenance record")
    public Response deleteMaintenance(@PathParam("id") int id) {
        if (!maintenanceService.delete(id)) {
            return ApiResponses.notFound("Maintenance record not found.");
        }
        return Response.noContent().build();
    }
}
