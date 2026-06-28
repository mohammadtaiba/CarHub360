package de.fherfurt.service;

import de.fherfurt.core.entity.Contract;
import de.fherfurt.core.entity.Customer;
import de.fherfurt.core.entity.RentVehicle;
import de.fherfurt.core.entity.SaleVehicle;
import de.fherfurt.core.repository.ContractRepository;
import de.fherfurt.core.repository.CustomerRepository;
import de.fherfurt.core.repository.RentVehicleRepository;
import de.fherfurt.core.repository.SaleVehicleRepository;
import de.fherfurt.core.validation.ContractValidator;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Stateless
public class ContractService {

    @Inject
    private ContractRepository contractRepository;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private SaleVehicleRepository saleVehicleRepository;

    @Inject
    private RentVehicleRepository rentVehicleRepository;

    @Inject
    private ContractValidator contractValidator;

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    public Contract findById(int contractId) {
        return contractRepository.findById(contractId);
    }

    public List<Contract> findByCustomerId(int customerId) {
        return contractRepository.findByCustomerId(customerId);
    }

    public List<Contract> findRentalContracts() {
        return contractRepository.findRentalContracts();
    }

    public List<Contract> findSaleContracts() {
        return contractRepository.findSaleContracts();
    }

    public Contract create(int customerId,
                           Integer saleVehicleId,
                           Integer rentVehicleId,
                           boolean rentalContract,
                           LocalDate contractDate,
                           LocalDate rentalStartDate,
                           LocalDate rentalEndDate) {
        Customer customer = customerRepository.findById(customerId);
        SaleVehicle saleVehicle = saleVehicleId == null ? null : saleVehicleRepository.findById(saleVehicleId);
        RentVehicle rentVehicle = rentVehicleId == null ? null : rentVehicleRepository.findById(rentVehicleId);

        validate(customer, saleVehicle, rentVehicle, rentalContract, rentalStartDate, rentalEndDate);
        if (rentalContract && !rentVehicle.isAvailable()) {
            throw new IllegalArgumentException("rentVehicleId references a vehicle that is not available.");
        }

        Contract contract = new Contract(
                0,
                customer,
                rentalContract ? null : saleVehicle,
                rentalContract ? rentVehicle : null,
                rentalContract,
                contractDate == null ? LocalDate.now() : contractDate,
                rentalContract ? rentalStartDate : null,
                rentalContract ? rentalEndDate : null
        );

        if (rentalContract) {
            rentVehicle.setAvailable(false);
            rentVehicleRepository.update(rentVehicle);
        }
        contractRepository.save(contract);
        return contract;
    }

    public Contract update(int contractId,
                           int customerId,
                           Integer saleVehicleId,
                           Integer rentVehicleId,
                           boolean rentalContract,
                           LocalDate contractDate,
                           LocalDate rentalStartDate,
                           LocalDate rentalEndDate) {
        Contract existing = contractRepository.findById(contractId);
        if (existing == null) {
            return null;
        }

        Customer customer = customerRepository.findById(customerId);
        SaleVehicle saleVehicle = saleVehicleId == null ? null : saleVehicleRepository.findById(saleVehicleId);
        RentVehicle rentVehicle = rentVehicleId == null ? null : rentVehicleRepository.findById(rentVehicleId);

        validate(customer, saleVehicle, rentVehicle, rentalContract, rentalStartDate, rentalEndDate);
        if (rentalContract
                && rentVehicle != null
                && !rentVehicle.isAvailable()
                && (existing.getRentVehicle() == null
                || existing.getRentVehicle().getVehicleId() != rentVehicle.getVehicleId())) {
            throw new IllegalArgumentException("rentVehicleId references a vehicle that is not available.");
        }

        releaseExistingRentalVehicle(existing, rentVehicle);

        existing.setCustomer(customer);
        existing.setSaleVehicle(rentalContract ? null : saleVehicle);
        existing.setRentVehicle(rentalContract ? rentVehicle : null);
        existing.setRentalContract(rentalContract);
        existing.setContractDate(contractDate == null ? LocalDate.now() : contractDate);
        existing.setRentalStartDate(rentalContract ? rentalStartDate : null);
        existing.setRentalEndDate(rentalContract ? rentalEndDate : null);

        if (rentalContract && rentVehicle != null) {
            rentVehicle.setAvailable(false);
            rentVehicleRepository.update(rentVehicle);
        }
        return contractRepository.update(existing);
    }

    public boolean delete(int contractId) {
        Contract existing = contractRepository.findById(contractId);
        if (existing == null) {
            return false;
        }
        if (existing.isRentalContract() && existing.getRentVehicle() != null) {
            RentVehicle vehicle = existing.getRentVehicle();
            vehicle.setAvailable(true);
            rentVehicleRepository.update(vehicle);
        }
        contractRepository.delete(contractId);
        return true;
    }

    public BigDecimal calculateRentalPrice(int contractId) {
        Contract contract = contractRepository.findById(contractId);
        if (contract == null || !contract.isRentalContract() || contract.getRentVehicle() == null) {
            return null;
        }
        long daysRented = ChronoUnit.DAYS.between(contract.getRentalStartDate(), contract.getRentalEndDate());
        return BigDecimal.valueOf(daysRented).multiply(contract.getRentVehicle().getDailyPrice());
    }

    private void validate(Customer customer,
                          SaleVehicle saleVehicle,
                          RentVehicle rentVehicle,
                          boolean rentalContract,
                          LocalDate rentalStartDate,
                          LocalDate rentalEndDate) {
        List<String> errors = contractValidator.validate(
                customer,
                saleVehicle,
                rentVehicle,
                rentalContract,
                rentalStartDate,
                rentalEndDate
        );
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }

    private void releaseExistingRentalVehicle(Contract existing, RentVehicle newRentVehicle) {
        if (!existing.isRentalContract() || existing.getRentVehicle() == null) {
            return;
        }
        if (newRentVehicle != null
                && existing.getRentVehicle().getVehicleId() == newRentVehicle.getVehicleId()) {
            return;
        }
        RentVehicle oldVehicle = existing.getRentVehicle();
        oldVehicle.setAvailable(true);
        rentVehicleRepository.update(oldVehicle);
    }
}
