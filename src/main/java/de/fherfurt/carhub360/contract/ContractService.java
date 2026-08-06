package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.vehicle.rent.RentVehicle;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class ContractService {

    @Inject
    private ContractRepository contractRepository;

    @Inject
    private ContractReferenceResolver contractReferenceResolver;

    @Inject
    private ContractValidationService contractValidationService;

    @Inject
    private ContractPriceCalculator contractPriceCalculator;

    @Inject
    private ContractRentalVehicleService contractRentalVehicleService;

    @Inject
    private ContractFactory contractFactory;

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
        ContractReferences references = contractReferenceResolver.resolve(customerId, saleVehicleId, rentVehicleId);

        contractValidationService.validate(references, rentalContract, rentalStartDate, rentalEndDate);
        contractRentalVehicleService.requireAvailableForCreate(rentalContract, references.rentVehicle());

        Contract contract = contractFactory.create(
                references.customer(),
                references.saleVehicle(),
                references.rentVehicle(),
                rentalContract,
                contractDate,
                rentalStartDate,
                rentalEndDate
        );

        contractRentalVehicleService.reserveIfRental(rentalContract, references.rentVehicle());
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

        ContractReferences references = contractReferenceResolver.resolve(customerId, saleVehicleId, rentVehicleId);
        RentVehicle rentVehicle = references.rentVehicle();

        contractValidationService.validate(references, rentalContract, rentalStartDate, rentalEndDate);
        contractRentalVehicleService.requireAvailableForUpdate(existing, rentalContract, rentVehicle);
        contractRentalVehicleService.releaseReplacedVehicle(existing, rentVehicle);

        contractFactory.apply(
                existing,
                references.customer(),
                references.saleVehicle(),
                rentVehicle,
                rentalContract,
                contractDate,
                rentalStartDate,
                rentalEndDate
        );

        contractRentalVehicleService.reserveIfRental(rentalContract, rentVehicle);
        return contractRepository.update(existing);
    }

    public boolean delete(int contractId) {
        Contract existing = contractRepository.findById(contractId);
        if (existing == null) {
            return false;
        }
        contractRentalVehicleService.releaseCurrentVehicle(existing);
        contractRepository.delete(contractId);
        return true;
    }

    public BigDecimal calculateRentalPrice(int contractId) {
        Contract contract = contractRepository.findById(contractId);
        return contractPriceCalculator.calculateRentalPrice(contract);
    }
}
