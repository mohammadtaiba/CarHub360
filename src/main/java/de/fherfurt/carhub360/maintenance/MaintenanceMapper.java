package de.fherfurt.carhub360.maintenance;

import de.fherfurt.carhub360.maintenance.dto.MaintenanceResponse;
import java.util.List;

final class MaintenanceMapper {

    private MaintenanceMapper() {
    }

    static List<MaintenanceResponse> toResponses(List<Maintenance> maintenances) {
        return maintenances.stream()
                .map(MaintenanceMapper::toResponse)
                .toList();
    }

    static MaintenanceResponse toResponse(Maintenance maintenance) {
        MaintenanceResponse response = new MaintenanceResponse();
        response.setMaintenanceId(maintenance.getMaintenanceId());
        response.setVehicleId(maintenance.getVehicle().getVehicleId());
        response.setMaintenanceStartDate(maintenance.getMaintenanceStartDate());
        response.setMaintenanceEndDate(maintenance.getMaintenanceEndDate());
        response.setMaintenanceCost(maintenance.getMaintenanceCost());
        response.setMaintenanceDescription(maintenance.getMaintenanceDescription());
        return response;
    }
}
