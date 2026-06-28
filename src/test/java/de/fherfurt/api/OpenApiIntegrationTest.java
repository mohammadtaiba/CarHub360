package de.fherfurt.api;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenApiIntegrationTest {

    @Test
    void openApiDefinitionIsPresent() {
        OpenAPIDefinition definition = WebApplication.class.getAnnotation(OpenAPIDefinition.class);

        assertNotNull(definition);
    }

    @Test
    void openApiDefinitionUsesDeploymentContextAsServer() {
        OpenAPIDefinition definition = WebApplication.class.getAnnotation(OpenAPIDefinition.class);

        assertEquals("/carhub360", definition.servers()[0].url());
    }

    @Test
    void allResourcesAreRegistered() {
        WebApplication app = new WebApplication();
        Set<Class<?>> classes = app.getClasses();

        assertTrue(classes.contains(CustomerResource.class));
        assertTrue(classes.contains(CustomerAddressResource.class));
        assertTrue(classes.contains(VehicleResource.class));
        assertTrue(classes.contains(SaleVehicleResource.class));
        assertTrue(classes.contains(RentVehicleResource.class));
        assertTrue(classes.contains(ContractResource.class));
        assertTrue(classes.contains(PaymentResource.class));
        assertTrue(classes.contains(MaintenanceResource.class));
        assertTrue(classes.contains(CustomerHistoryResource.class));
        assertTrue(classes.contains(ValidationExceptionMapper.class));
        assertTrue(classes.contains(OpenApiResource.class));
    }

    @Test
    void swaggerUiPointsToGeneratedOpenApiSpec() throws Exception {
        Path ui = Path.of("src/main/webapp/swagger-ui/index.html");
        String markup = Files.readString(ui);

        assertTrue(Files.exists(ui));
        assertTrue(markup.contains("../api/openapi.json"));
        assertTrue(markup.contains("../webjars/swagger-ui/"));
    }
}
