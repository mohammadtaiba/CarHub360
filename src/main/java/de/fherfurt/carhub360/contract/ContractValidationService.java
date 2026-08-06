package de.fherfurt.carhub360.contract;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class ContractValidationService {

    @Inject
    private ContractValidator contractValidator;

    public void validate(ContractReferences references,
                         boolean rentalContract,
                         LocalDate rentalStartDate,
                         LocalDate rentalEndDate) {
        List<String> errors = contractValidator.validate(
                references.customer(),
                references.saleVehicle(),
                references.rentVehicle(),
                rentalContract,
                rentalStartDate,
                rentalEndDate
        );
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
