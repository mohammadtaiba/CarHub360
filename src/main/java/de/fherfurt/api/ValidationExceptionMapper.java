package de.fherfurt.api;

import de.fherfurt.api.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<String> details = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                .toList();
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Validation failed.", details))
                .build();
    }
}
