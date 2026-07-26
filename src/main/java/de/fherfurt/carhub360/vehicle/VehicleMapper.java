package de.fherfurt.carhub360.vehicle;

import de.fherfurt.carhub360.vehicle.dto.VehicleRequest;

public final class VehicleMapper {

    private VehicleMapper() {
    }

    static Vehicle toVehicle(VehicleRequest request) {
        Vehicle vehicle = new Vehicle();
        applyVehicleFields(request, vehicle);
        return vehicle;
    }

    public static void applyVehicleFields(VehicleRequest request, Vehicle vehicle) {
        vehicle.setName(request.getName());
        vehicle.setBrand(request.getBrand());
        vehicle.setKilometerCount(request.getKilometerCount());
        vehicle.setConstructionYear(request.getConstructionYear());
        vehicle.setType(request.getType());
    }
}
