package de.fherfurt.carhub360.vehicle.sale;

import de.fherfurt.carhub360.shared.api.ApiResponses;
import de.fherfurt.carhub360.vehicle.sale.dto.SaleVehicleCreateRequest;
import de.fherfurt.carhub360.vehicle.sale.dto.SaleVehicleUpdateRequest;
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
        return Response.ok(SaleVehicleMapper.toResponses(saleVehicleService.findAll())).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one sale vehicle")
    public Response getSaleVehicle(@PathParam("id") int id) {
        return ApiResponses.okOrNotFound(
                saleVehicleService.findById(id),
                SaleVehicleMapper::toResponse,
                "Sale vehicle not found."
        );
    }

    @POST
    @Operation(summary = "Create a sale vehicle")
    public Response createSaleVehicle(@Valid SaleVehicleCreateRequest request) {
        try {
            SaleVehicle created = saleVehicleService.create(SaleVehicleMapper.toSaleVehicle(request));
            return ApiResponses.created(SaleVehicleMapper.toResponse(created));
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a sale vehicle")
    public Response updateSaleVehicle(@PathParam("id") int id, @Valid SaleVehicleUpdateRequest request) {
        try {
            SaleVehicle updated = saleVehicleService.update(id, SaleVehicleMapper.toSaleVehicle(request));
            return ApiResponses.okOrNotFound(updated, SaleVehicleMapper::toResponse, "Sale vehicle not found.");
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a sale vehicle")
    public Response deleteSaleVehicle(@PathParam("id") int id) {
        return ApiResponses.noContentOrNotFound(saleVehicleService.delete(id), "Sale vehicle not found.");
    }
}
