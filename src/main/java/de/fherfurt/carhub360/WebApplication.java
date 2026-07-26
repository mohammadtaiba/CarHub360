package de.fherfurt.carhub360;

import de.fherfurt.carhub360.contract.ContractResource;
import de.fherfurt.carhub360.customer.address.CustomerAddressResource;
import de.fherfurt.carhub360.customer.CustomerResource;
import de.fherfurt.carhub360.customer.history.CustomerHistoryResource;
import de.fherfurt.carhub360.maintenance.MaintenanceResource;
import de.fherfurt.carhub360.payment.PaymentResource;
import de.fherfurt.carhub360.shared.api.ValidationExceptionMapper;
import de.fherfurt.carhub360.vehicle.rent.RentVehicleResource;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicleResource;
import de.fherfurt.carhub360.vehicle.VehicleResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
@OpenAPIDefinition(
        info = @Info(
                title = "CarHub360 API",
                version = "1.0",
                description = "REST API for vehicle sales, rentals, customers, contracts and operations"
        ),
        servers = @Server(
                url = "/carhub360",
                description = "Local WildFly deployment"
        )
)
public class WebApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(CustomerResource.class);
        classes.add(CustomerAddressResource.class);
        classes.add(VehicleResource.class);
        classes.add(SaleVehicleResource.class);
        classes.add(RentVehicleResource.class);
        classes.add(ContractResource.class);
        classes.add(PaymentResource.class);
        classes.add(MaintenanceResource.class);
        classes.add(CustomerHistoryResource.class);
        classes.add(ValidationExceptionMapper.class);
        classes.add(OpenApiResource.class);
        return classes;
    }
}
