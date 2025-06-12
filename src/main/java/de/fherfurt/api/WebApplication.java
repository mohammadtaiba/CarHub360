package de.fherfurt.api;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * Main JAX-RS application configuration. This class also registers the
 * {@link OpenApiResource} to expose the generated OpenAPI specification and
 * provides basic information via {@link OpenAPIDefinition}.
 */
@ApplicationPath("/api")
@OpenAPIDefinition(
        info = @Info(
                title = "CarHub360 API",
                version = "1.0",
                description = "API documentation for CarHub360"
        )
)
public class WebApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(CustomerResource.class);
        classes.add(ContractResource.class);
        classes.add(OpenApiResource.class);
        return classes;
    }
}