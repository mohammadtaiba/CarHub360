package de.fherfurt.carhub360.contract;

import jakarta.ejb.Stateless;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Stateless
public class ContractPriceCalculator {

    public BigDecimal calculateRentalPrice(Contract contract) {
        if (contract == null || !contract.isRentalContract() || contract.getRentVehicle() == null) {
            return null;
        }
        long daysRented = ChronoUnit.DAYS.between(contract.getRentalStartDate(), contract.getRentalEndDate());
        return BigDecimal.valueOf(daysRented).multiply(contract.getRentVehicle().getDailyPrice());
    }
}
