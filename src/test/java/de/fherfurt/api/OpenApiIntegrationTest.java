package de.fherfurt.api;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Set;

import static org.junit.Assert.*;

public class OpenApiIntegrationTest {

    @Test
    public void testOpenApiDefinitionPresent() {
        OpenAPIDefinition def = WebApplication.class.getAnnotation(OpenAPIDefinition.class);
        assertNotNull("OpenAPIDefinition annotation missing", def);
    }

    @Test
    public void testOpenApiResourceRegistered() {
        WebApplication app = new WebApplication();
        Set<Class<?>> classes = app.getClasses();
        assertTrue("OpenApiResource not registered", classes.contains(OpenApiResource.class));
    }

    @Test
    public void testSwaggerUiExists() throws Exception {
        Path ui = Paths.get("src/main/webapp/swagger-ui/index.html");
        assertTrue("Swagger UI index.html missing", Files.exists(ui));
        String content = Files.readString(ui);
        assertTrue("Swagger UI does not load OpenAPI spec", content.contains("../api/openapi"));
    }
}