package de.fherfurt.carhub360.contract;

import de.fherfurt.carhub360.contract.dto.ContractResponse;
import java.util.List;

final class ContractMapper {

    private ContractMapper() {
    }

    static List<ContractResponse> toResponses(List<Contract> contracts) {
        return contracts.stream()
                .map(ContractMapper::toResponse)
                .toList();
    }

    static ContractResponse toResponse(Contract contract) {
        ContractResponse response = new ContractResponse();
        response.setContractId(contract.getContractId());
        response.setCustomerId(contract.getCustomer().getCustomerId());
        response.setSaleVehicleId(contract.getSaleVehicle() == null ? null : contract.getSaleVehicle().getVehicleId());
        response.setRentVehicleId(contract.getRentVehicle() == null ? null : contract.getRentVehicle().getVehicleId());
        response.setRentalContract(contract.isRentalContract());
        response.setContractDate(contract.getContractDate());
        response.setRentalStartDate(contract.getRentalStartDate());
        response.setRentalEndDate(contract.getRentalEndDate());
        return response;
    }
}
