package de.fherfurt.carhub360.vehicle.rent;

import de.fherfurt.carhub360.vehicle.VehicleMapper;
import de.fherfurt.carhub360.vehicle.dto.RentVehicleRequest;

final class RentVehicleMapper {

    private RentVehicleMapper() {
    }

    static RentVehicle toRentVehicle(RentVehicleRequest request) {
        RentVehicle vehicle = new RentVehicle();
        VehicleMapper.applyVehicleFields(request, vehicle);
        vehicle.setAvailable(request.isAvailable());
        vehicle.setDailyPrice(request.getDailyPrice());
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setDeposit(request.getDeposit());
        return vehicle;
    }
}
