package de.fherfurt.carhub360.shared.api;

import de.fherfurt.carhub360.shared.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;

public final class ApiResponses {

    private ApiResponses() {
    }

    public static Response badRequest(IllegalArgumentException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }

    public static Response notFound(String message) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(message))
                .build();
    }
}
