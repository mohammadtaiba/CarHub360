package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicle;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ContractPriceCalculatorTest {

    private final ContractPriceCalculator calculator = new ContractPriceCalculator();

    @Test
    void calculateRentalPriceMultipliesRentalDaysByDailyPrice() {
        Contract contract = rentalContract(BigDecimal.valueOf(79), 3);

        assertEquals(BigDecimal.valueOf(237), calculator.calculateRentalPrice(contract));
    }

    @Test
    void calculateRentalPriceReturnsNullForMissingContract() {
        assertNull(calculator.calculateRentalPrice(null));
    }

    @Test
    void calculateRentalPriceReturnsNullForSaleContract() {
        Contract contract = new Contract(
                0,
                null,
                new SaleVehicle(),
                null,
                false,
                LocalDate.now(),
                null,
                null
        );

        assertNull(calculator.calculateRentalPrice(contract));
    }

    @Test
    void calculateRentalPriceReturnsNullForRentalContractWithoutVehicle() {
        Contract contract = new Contract(
                0,
                null,
                null,
                null,
                true,
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        assertNull(calculator.calculateRentalPrice(contract));
    }

    private Contract rentalContract(BigDecimal dailyPrice, int days) {
        RentVehicle rentVehicle = new RentVehicle();
        rentVehicle.setDailyPrice(dailyPrice);

        LocalDate startDate = LocalDate.now();
        return new Contract(
                0,
                null,
                null,
                rentVehicle,
                true,
                startDate,
                startDate,
                startDate.plusDays(days)
        );
    }
}
