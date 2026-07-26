package de.fherfurt.carhub360.vehicle;

import de.fherfurt.carhub360.shared.api.ApiResponses;
import de.fherfurt.carhub360.vehicle.dto.VehicleCreateRequest;
import de.fherfurt.carhub360.vehicle.dto.VehicleUpdateRequest;
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

@Path("/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Vehicles", description = "Base vehicle inventory")
public class VehicleResource {

    @Inject
    private VehicleService vehicleService;

    @GET
    @Operation(summary = "List vehicles")
    public Response getVehicles() {
        return Response.ok(VehicleMapper.toResponses(vehicleService.findAll())).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one vehicle")
    public Response getVehicle(@PathParam("id") int id) {
        Vehicle vehicle = vehicleService.findById(id);
        if (vehicle == null) {
            return ApiResponses.notFound("Vehicle not found.");
        }
        return Response.ok(VehicleMapper.toResponse(vehicle)).build();
    }

    @POST
    @Operation(summary = "Create a base vehicle")
    public Response createVehicle(@Valid VehicleCreateRequest request) {
        try {
            Vehicle created = vehicleService.create(VehicleMapper.toVehicle(request));
            return Response.status(Response.Status.CREATED)
                    .entity(VehicleMapper.toResponse(created))
                    .build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a base vehicle")
    public Response updateVehicle(@PathParam("id") int id, @Valid VehicleUpdateRequest request) {
        try {
            Vehicle updated = vehicleService.update(id, VehicleMapper.toVehicle(request));
            if (updated == null) {
                return ApiResponses.notFound("Vehicle not found.");
            }
            return Response.ok(VehicleMapper.toResponse(updated)).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a vehicle")
    public Response deleteVehicle(@PathParam("id") int id) {
        if (!vehicleService.delete(id)) {
            return ApiResponses.notFound("Vehicle not found.");
        }
        return Response.noContent().build();
    }
}
