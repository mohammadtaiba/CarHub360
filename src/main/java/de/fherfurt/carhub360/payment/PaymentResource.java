package de.fherfurt.carhub360.payment;

import de.fherfurt.carhub360.payment.dto.PaymentCreateRequest;
import de.fherfurt.carhub360.payment.dto.PaymentUpdateRequest;
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
            return Response.ok(PaymentMapper.toResponses(paymentService.findByCustomerId(customerId))).build();
        }
        return Response.ok(PaymentMapper.toResponses(paymentService.findAll())).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one payment")
    public Response getPayment(@PathParam("id") int id) {
        return ApiResponses.okOrNotFound(
                paymentService.findById(id),
                PaymentMapper::toResponse,
                "Payment not found."
        );
    }

    @POST
    @Operation(summary = "Create a payment")
    public Response createPayment(@Valid PaymentCreateRequest request) {
        try {
            Payment created = paymentService.create(
                    request.getCustomerId(),
                    request.getPaymentMethod(),
                    request.getPaymentStatus(),
                    request.getPaymentAmount()
            );
            return ApiResponses.created(PaymentMapper.toResponse(created));
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a payment")
    public Response updatePayment(@PathParam("id") int id, @Valid PaymentUpdateRequest request) {
        try {
            Payment updated = paymentService.update(
                    id,
                    request.getCustomerId(),
                    request.getPaymentMethod(),
                    request.getPaymentStatus(),
                    request.getPaymentAmount()
            );
            return ApiResponses.okOrNotFound(updated, PaymentMapper::toResponse, "Payment not found.");
        } catch (IllegalArgumentException exception) {
            return ApiResponses.badRequest(exception);
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a payment")
    public Response deletePayment(@PathParam("id") int id) {
        return ApiResponses.noContentOrNotFound(paymentService.delete(id), "Payment not found.");
    }
}
