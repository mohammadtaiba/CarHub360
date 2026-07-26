package de.fherfurt.carhub360.vehicle.sale;

import de.fherfurt.carhub360.vehicle.VehicleMapper;
import de.fherfurt.carhub360.vehicle.dto.SaleVehicleRequest;
import de.fherfurt.carhub360.vehicle.dto.SaleVehicleResponse;
import java.util.List;

final class SaleVehicleMapper {

    private SaleVehicleMapper() {
    }

    static SaleVehicle toSaleVehicle(SaleVehicleRequest request) {
        SaleVehicle vehicle = new SaleVehicle();
        VehicleMapper.applyVehicleFields(request, vehicle);
        vehicle.setSalePrice(request.getSalePrice());
        vehicle.setNewVehicle(request.isNewVehicle());
        return vehicle;
    }

    static List<SaleVehicleResponse> toResponses(List<SaleVehicle> vehicles) {
        return vehicles.stream()
                .map(SaleVehicleMapper::toResponse)
                .toList();
    }

    static SaleVehicleResponse toResponse(SaleVehicle vehicle) {
        SaleVehicleResponse response = new SaleVehicleResponse();
        VehicleMapper.applyVehicleResponseFields(vehicle, response);
        response.setSalePrice(vehicle.getSalePrice());
        response.setNewVehicle(vehicle.isNewVehicle());
        return response;
    }
}
