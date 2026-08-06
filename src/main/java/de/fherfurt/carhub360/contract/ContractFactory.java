package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.customer.Customer;
import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import de.fherfurt.carhub360.vehicle.sale.SaleVehicle;
import jakarta.ejb.Stateless;
import java.time.LocalDate;

@Stateless
public class ContractFactory {

    public Contract create(Customer customer,
                           SaleVehicle saleVehicle,
                           RentVehicle rentVehicle,
                           boolean rentalContract,
                           LocalDate contractDate,
                           LocalDate rentalStartDate,
                           LocalDate rentalEndDate) {
        return new Contract(
                0,
                customer,
                rentalContract ? null : saleVehicle,
                rentalContract ? rentVehicle : null,
                rentalContract,
                effectiveContractDate(contractDate),
                rentalContract ? rentalStartDate : null,
                rentalContract ? rentalEndDate : null
        );
    }

    public void apply(Contract contract,
                      Customer customer,
                      SaleVehicle saleVehicle,
                      RentVehicle rentVehicle,
                      boolean rentalContract,
                      LocalDate contractDate,
                      LocalDate rentalStartDate,
                      LocalDate rentalEndDate) {
        contract.setCustomer(customer);
        contract.setSaleVehicle(rentalContract ? null : saleVehicle);
        contract.setRentVehicle(rentalContract ? rentVehicle : null);
        contract.setRentalContract(rentalContract);
        contract.setContractDate(effectiveContractDate(contractDate));
        contract.setRentalStartDate(rentalContract ? rentalStartDate : null);
        contract.setRentalEndDate(rentalContract ? rentalEndDate : null);
    }

    private LocalDate effectiveContractDate(LocalDate contractDate) {
        return contractDate == null ? LocalDate.now() : contractDate;
    }
}
