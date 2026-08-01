package de.fherfurt.carhub360.shared.api;

import de.fherfurt.carhub360.shared.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;
import java.util.function.Function;

public final class ApiResponses {

    private ApiResponses() {
    }

    public static Response badRequest(IllegalArgumentException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }

    public static Response created(Object entity) {
        return Response.status(Response.Status.CREATED)
                .entity(entity)
                .build();
    }

    public static Response notFound(String message) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(message))
                .build();
    }

    public static <T> Response okOrNotFound(T entity, Function<T, ?> mapper, String notFoundMessage) {
        if (entity == null) {
            return notFound(notFoundMessage);
        }
        return Response.ok(mapper.apply(entity)).build();
    }

    public static Response noContentOrNotFound(boolean exists, String notFoundMessage) {
        if (!exists) {
            return notFound(notFoundMessage);
        }
        return Response.noContent().build();
    }
}
