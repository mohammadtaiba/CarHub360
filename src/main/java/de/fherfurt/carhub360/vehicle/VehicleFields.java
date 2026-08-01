package de.fherfurt.carhub360.vehicle;

import java.time.Year;

public final class VehicleFields {

    private VehicleFields() {
    }

    public static void validate(Vehicle vehicle, String requiredMessage) {
        if (vehicle == null) {
            throw new IllegalArgumentException(requiredMessage);
        }
        requireText(vehicle.getName(), "name");
        requireText(vehicle.getBrand(), "brand");
        requireText(vehicle.getType(), "type");
        if (vehicle.getKilometerCount() < 0) {
            throw new IllegalArgumentException("kilometerCount must not be negative.");
        }
        int currentYear = Year.now().getValue();
        if (vehicle.getConstructionYear() < 1900 || vehicle.getConstructionYear() > currentYear + 1) {
            throw new IllegalArgumentException("constructionYear must be plausible.");
        }
    }

    public static void copy(Vehicle source, Vehicle target) {
        target.setName(source.getName());
        target.setBrand(source.getBrand());
        target.setKilometerCount(source.getKilometerCount());
        target.setConstructionYear(source.getConstructionYear());
        target.setType(source.getType());
    }

    public static void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
    }
}
