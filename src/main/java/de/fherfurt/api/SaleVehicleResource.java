package de.fherfurt.api;

import de.fherfurt.api.dto.SaleVehicleRequest;
import de.fherfurt.core.entity.SaleVehicle;
import de.fherfurt.service.SaleVehicleService;
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

@Path("/sale-vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Sale Vehicles", description = "Vehicles that can be sold")
public class SaleVehicleResource {

    @Inject
    private SaleVehicleService saleVehicleService;

    @GET
    @Operation(summary = "List sale vehicles")
    public Response getSaleVehicles() {
        return Response.ok(saleVehicleService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one sale vehicle")
    public Response getSaleVehicle(@PathParam("id") int id) {
        SaleVehicle vehicle = saleVehicleService.findById(id);
        if (vehicle == null) {
            return ApiResponses.notFound("Sale vehicle not found.");
        }
        return Response.ok(vehicle).build();
    }

    @POST
    @Operation(summary = "Create a sale vehicle")
    public Response createSaleVehicle(@Valid SaleVehicleRequest request) {
        try {
            SaleVehicle created = saleVehicleService.create(toSaleVehicle(request));
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a sale vehicle")
    public Response updateSaleVehicle(@PathParam("id") int id, @Valid SaleVehicleRequest request) {
        try {
            SaleVehicle updated = saleVehicleService.update(id, toSaleVehicle(request));
            if (updated == null) {
                return ApiResponses.notFound("Sale vehicle not found.");
            }
            return Response.ok(updated).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a sale vehicle")
    public Response deleteSaleVehicle(@PathParam("id") int id) {
        if (!saleVehicleService.delete(id)) {
            return ApiResponses.notFound("Sale vehicle not found.");
        }
        return Response.noContent().build();
    }

    private SaleVehicle toSaleVehicle(SaleVehicleRequest request) {
        SaleVehicle vehicle = new SaleVehicle();
        vehicle.setName(request.getName());
        vehicle.setBrand(request.getBrand());
        vehicle.setKilometerCount(request.getKilometerCount());
        vehicle.setConstructionYear(request.getConstructionYear());
        vehicle.setType(request.getType());
        vehicle.setSalePrice(request.getSalePrice());
        vehicle.setNewVehicle(request.isNewVehicle());
        return vehicle;
    }
}
