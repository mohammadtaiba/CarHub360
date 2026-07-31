package de.fherfurt.carhub360.vehicle;

import de.fherfurt.carhub360.vehicle.dto.VehicleRequest;
import de.fherfurt.carhub360.vehicle.dto.VehicleResponse;
import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import de.fherfurt.carhub360.vehicle.rent.RentVehicleMapper;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicle;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicleMapper;
import java.util.List;

public final class VehicleMapper {

    private VehicleMapper() {
    }

    static Vehicle toVehicle(VehicleRequest request) {
        Vehicle vehicle = new Vehicle();
        applyVehicleFields(request, vehicle);
        return vehicle;
    }

    static List<VehicleResponse> toResponses(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(VehicleMapper::toResponse)
                .toList();
    }

    static VehicleResponse toResponse(Vehicle vehicle) {
        if (vehicle instanceof SaleVehicle saleVehicle) {
            return SaleVehicleMapper.toResponse(saleVehicle);
        }
        if (vehicle instanceof RentVehicle rentVehicle) {
            return RentVehicleMapper.toResponse(rentVehicle);
        }
        VehicleResponse response = new VehicleResponse();
        applyVehicleResponseFields(vehicle, response);
        return response;
    }

    public static void applyVehicleFields(VehicleRequest request, Vehicle vehicle) {
        vehicle.setName(request.getName());
        vehicle.setBrand(request.getBrand());
        vehicle.setKilometerCount(request.getKilometerCount());
        vehicle.setConstructionYear(request.getConstructionYear());
        vehicle.setType(request.getType());
    }

    public static void applyVehicleResponseFields(Vehicle vehicle, VehicleResponse response) {
        response.setVehicleId(vehicle.getVehicleId());
        response.setName(vehicle.getName());
        response.setBrand(vehicle.getBrand());
        response.setKilometerCount(vehicle.getKilometerCount());
        response.setConstructionYear(vehicle.getConstructionYear());
        response.setType(vehicle.getType());
    }
}
