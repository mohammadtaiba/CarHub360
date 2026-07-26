package de.fherfurt.carhub360.vehicle.sale;

import de.fherfurt.carhub360.vehicle.VehicleMapper;
import de.fherfurt.carhub360.vehicle.dto.SaleVehicleRequest;

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
}
