package de.fherfurt.carhub360.maintenance;

import de.fherfurt.carhub360.maintenance.dto.MaintenanceCreateRequest;
import de.fherfurt.carhub360.maintenance.dto.MaintenanceUpdateRequest;
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
import jakarta.ws.rs.QueryParam;

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
            return Response.ok(MaintenanceMapper.toResponses(maintenanceService.findByVehicleId(vehicleId))).build();
        }
        return Response.ok(MaintenanceMapper.toResponses(maintenanceService.findAll())).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one maintenance record")
    public Response getMaintenance(@PathParam("id") int id) {
        return ApiResponses.okOrNotFound(
                maintenanceService.findById(id),
                MaintenanceMapper::toResponse,
                "Maintenance record not found."
        );
    }

    @POST
    @Operation(summary = "Create a maintenance record")
    public Response createMaintenance(@Valid MaintenanceCreateRequest request) {
        try {
            Maintenance created = maintenanceService.create(
                    request.getVehicleId(),
                    request.getMaintenanceStartDate(),
                    request.getMaintenanceEndDate(),
                    request.getMaintenanceCost(),
                    request.getMaintenanceDescription()
            );
            return ApiResponses.created(MaintenanceMapper.toResponse(created));
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a maintenance record")
    public Response updateMaintenance(@PathParam("id") int id, @Valid MaintenanceUpdateRequest request) {
        try {
            Maintenance updated = maintenanceService.update(
                    id,
                    request.getVehicleId(),
                    request.getMaintenanceStartDate(),
                    request.getMaintenanceEndDate(),
                    request.getMaintenanceCost(),
                    request.getMaintenanceDescription()
            );
            return ApiResponses.okOrNotFound(updated, MaintenanceMapper::toResponse, "Maintenance record not found.");
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a maintenance record")
    public Response deleteMaintenance(@PathParam("id") int id) {
        return ApiResponses.noContentOrNotFound(maintenanceService.delete(id), "Maintenance record not found.");
    }
}
