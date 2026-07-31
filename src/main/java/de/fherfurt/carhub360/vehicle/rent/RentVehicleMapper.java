package de.fherfurt.carhub360.vehicle.rent;

import de.fherfurt.carhub360.vehicle.VehicleMapper;
import de.fherfurt.carhub360.vehicle.rent.dto.RentVehicleRequest;
import de.fherfurt.carhub360.vehicle.rent.dto.RentVehicleResponse;
import java.util.List;

public final class RentVehicleMapper {

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

    static List<RentVehicleResponse> toResponses(List<RentVehicle> vehicles) {
        return vehicles.stream()
                .map(RentVehicleMapper::toResponse)
                .toList();
    }

    public static RentVehicleResponse toResponse(RentVehicle vehicle) {
        RentVehicleResponse response = new RentVehicleResponse();
        VehicleMapper.applyVehicleResponseFields(vehicle, response);
        response.setAvailable(vehicle.isAvailable());
        response.setDailyPrice(vehicle.getDailyPrice());
        response.setLicensePlate(vehicle.getLicensePlate());
        response.setDeposit(vehicle.getDeposit());
        return response;
    }
}
