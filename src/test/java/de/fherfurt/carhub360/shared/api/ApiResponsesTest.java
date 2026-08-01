package de.fherfurt.carhub360.shared.api;

import de.fherfurt.carhub360.shared.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ApiResponsesTest {

    @Test
    void badRequestReturnsErrorResponse() {
        Response response = ApiResponses.badRequest(new IllegalArgumentException("invalid payload"));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        ErrorResponse error = assertInstanceOf(ErrorResponse.class, response.getEntity());
        assertEquals("invalid payload", error.getMessage());
    }

    @Test
    void createdReturnsCreatedEntity() {
        Response response = ApiResponses.created("created body");

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("created body", response.getEntity());
    }

    @Test
    void notFoundReturnsErrorResponse() {
        Response response = ApiResponses.notFound("vehicle not found");

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        ErrorResponse error = assertInstanceOf(ErrorResponse.class, response.getEntity());
        assertEquals("vehicle not found", error.getMessage());
    }

    @Test
    void okOrNotFoundReturnsMappedEntity() {
        Response response = ApiResponses.okOrNotFound("vehicle", String::toUpperCase, "vehicle not found");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("VEHICLE", response.getEntity());
    }

    @Test
    void okOrNotFoundReturnsErrorResponseForNullEntity() {
        Response response = ApiResponses.<String>okOrNotFound(null, String::toUpperCase, "vehicle not found");

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        ErrorResponse error = assertInstanceOf(ErrorResponse.class, response.getEntity());
        assertEquals("vehicle not found", error.getMessage());
    }

    @Test
    void noContentOrNotFoundReturnsNoContent() {
        Response response = ApiResponses.noContentOrNotFound(true, "vehicle not found");

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void noContentOrNotFoundReturnsErrorResponseForMissingEntity() {
        Response response = ApiResponses.noContentOrNotFound(false, "vehicle not found");

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        ErrorResponse error = assertInstanceOf(ErrorResponse.class, response.getEntity());
        assertEquals("vehicle not found", error.getMessage());
    }
}
