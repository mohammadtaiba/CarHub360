package de.fherfurt.api;

import de.fherfurt.api.dto.RentVehicleRequest;
import de.fherfurt.core.entity.RentVehicle;
import de.fherfurt.service.RentVehicleService;
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

@Path("/rent-vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Rent Vehicles", description = "Vehicles that can be rented")
public class RentVehicleResource {

    @Inject
    private RentVehicleService rentVehicleService;

    @GET
    @Operation(summary = "List rent vehicles")
    public Response getRentVehicles() {
        return Response.ok(rentVehicleService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one rent vehicle")
    public Response getRentVehicle(@PathParam("id") int id) {
        RentVehicle vehicle = rentVehicleService.findById(id);
        if (vehicle == null) {
            return ApiResponses.notFound("Rent vehicle not found.");
        }
        return Response.ok(vehicle).build();
    }

    @POST
    @Operation(summary = "Create a rent vehicle")
    public Response createRentVehicle(@Valid RentVehicleRequest request) {
        try {
            RentVehicle created = rentVehicleService.create(toRentVehicle(request));
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a rent vehicle")
    public Response updateRentVehicle(@PathParam("id") int id, @Valid RentVehicleRequest request) {
        try {
            RentVehicle updated = rentVehicleService.update(id, toRentVehicle(request));
            if (updated == null) {
                return ApiResponses.notFound("Rent vehicle not found.");
            }
            return Response.ok(updated).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a rent vehicle")
    public Response deleteRentVehicle(@PathParam("id") int id) {
        if (!rentVehicleService.delete(id)) {
            return ApiResponses.notFound("Rent vehicle not found.");
        }
        return Response.noContent().build();
    }

    private RentVehicle toRentVehicle(RentVehicleRequest request) {
        RentVehicle vehicle = new RentVehicle();
        vehicle.setName(request.getName());
        vehicle.setBrand(request.getBrand());
        vehicle.setKilometerCount(request.getKilometerCount());
        vehicle.setConstructionYear(request.getConstructionYear());
        vehicle.setType(request.getType());
        vehicle.setAvailable(request.isAvailable());
        vehicle.setDailyPrice(request.getDailyPrice());
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setDeposit(request.getDeposit());
        return vehicle;
    }
}
