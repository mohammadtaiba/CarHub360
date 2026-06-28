package de.fherfurt.api;

import de.fherfurt.api.dto.VehicleRequest;
import de.fherfurt.core.entity.Vehicle;
import de.fherfurt.service.VehicleService;
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
        return Response.ok(vehicleService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one vehicle")
    public Response getVehicle(@PathParam("id") int id) {
        Vehicle vehicle = vehicleService.findById(id);
        if (vehicle == null) {
            return ApiResponses.notFound("Vehicle not found.");
        }
        return Response.ok(vehicle).build();
    }

    @POST
    @Operation(summary = "Create a base vehicle")
    public Response createVehicle(@Valid VehicleRequest request) {
        try {
            Vehicle created = vehicleService.create(toVehicle(request));
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a base vehicle")
    public Response updateVehicle(@PathParam("id") int id, @Valid VehicleRequest request) {
        try {
            Vehicle updated = vehicleService.update(id, toVehicle(request));
            if (updated == null) {
                return ApiResponses.notFound("Vehicle not found.");
            }
            return Response.ok(updated).build();
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

    private Vehicle toVehicle(VehicleRequest request) {
        Vehicle vehicle = new Vehicle();
        vehicle.setName(request.getName());
        vehicle.setBrand(request.getBrand());
        vehicle.setKilometerCount(request.getKilometerCount());
        vehicle.setConstructionYear(request.getConstructionYear());
        vehicle.setType(request.getType());
        return vehicle;
    }
}
