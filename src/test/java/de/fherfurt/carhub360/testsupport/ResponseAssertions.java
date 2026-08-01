package de.fherfurt.carhub360.testsupport;

import de.fherfurt.carhub360.shared.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public final class ResponseAssertions {

    private ResponseAssertions() {
    }

    public static ErrorResponse assertNotFound(Response response, String message) {
        return assertError(response, Response.Status.NOT_FOUND, message);
    }

    public static ErrorResponse assertError(Response response, Response.Status status, String message) {
        assertEquals(status.getStatusCode(), response.getStatus());
        ErrorResponse error = assertInstanceOf(ErrorResponse.class, response.getEntity());
        assertEquals(message, error.getMessage());
        return error;
    }
}
