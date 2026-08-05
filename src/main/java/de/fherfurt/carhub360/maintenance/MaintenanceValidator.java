package de.fherfurt.carhub360.maintenance;

import de.fherfurt.carhub360.shared.validation.RequiredText;
import jakarta.ejb.Stateless;
import java.math.BigDecimal;
import java.util.Date;

@Stateless
public class MaintenanceValidator {

    public void validate(Date startDate, Date endDate, BigDecimal cost, String description) {
        if (startDate == null) {
            throw new IllegalArgumentException("maintenanceStartDate is required.");
        }
        if (endDate != null && endDate.before(startDate)) {
            throw new IllegalArgumentException("maintenanceEndDate must not be before maintenanceStartDate.");
        }
        if (cost == null || cost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("maintenanceCost must not be negative.");
        }
        RequiredText.require(description, "maintenanceDescription");
    }
}
