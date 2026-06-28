package de.fherfurt.api;

import de.fherfurt.api.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;

final class ApiResponses {

    private ApiResponses() {
    }

    static Response badRequest(IllegalArgumentException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }

    static Response notFound(String message) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(message))
                .build();
    }
}
