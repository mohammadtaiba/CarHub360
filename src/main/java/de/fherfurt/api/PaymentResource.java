package de.fherfurt.api;

import de.fherfurt.api.dto.PaymentRequest;
import de.fherfurt.core.entity.Payment;
import de.fherfurt.service.PaymentService;
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

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Payments", description = "Customer payment records")
public class PaymentResource {

    @Inject
    private PaymentService paymentService;

    @GET
    @Operation(summary = "List payments")
    public Response getPayments(@QueryParam("customerId") Integer customerId) {
        if (customerId != null) {
            return Response.ok(paymentService.findByCustomerId(customerId)).build();
        }
        return Response.ok(paymentService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one payment")
    public Response getPayment(@PathParam("id") int id) {
        Payment payment = paymentService.findById(id);
        if (payment == null) {
            return ApiResponses.notFound("Payment not found.");
        }
        return Response.ok(payment).build();
    }

    @POST
    @Operation(summary = "Create a payment")
    public Response createPayment(@Valid PaymentRequest request) {
        try {
            Payment created = paymentService.create(
                    request.getCustomerId(),
                    request.getPaymentMethod(),
                    request.getPaymentStatus(),
                    request.getPaymentAmount()
            );
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a payment")
    public Response updatePayment(@PathParam("id") int id, @Valid PaymentRequest request) {
        try {
            Payment updated = paymentService.update(
                    id,
                    request.getCustomerId(),
                    request.getPaymentMethod(),
                    request.getPaymentStatus(),
                    request.getPaymentAmount()
            );
            if (updated == null) {
                return ApiResponses.notFound("Payment not found.");
            }
            return Response.ok(updated).build();
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a payment")
    public Response deletePayment(@PathParam("id") int id) {
        if (!paymentService.delete(id)) {
            return ApiResponses.notFound("Payment not found.");
        }
        return Response.noContent().build();
    }
}
