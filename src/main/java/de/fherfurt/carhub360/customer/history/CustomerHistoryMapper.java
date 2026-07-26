package de.fherfurt.carhub360.customer.history;

import de.fherfurt.carhub360.customer.history.dto.CustomerHistoryResponse;
import java.util.List;

final class CustomerHistoryMapper {

    private CustomerHistoryMapper() {
    }

    static List<CustomerHistoryResponse> toResponses(List<CustomerHistory> histories) {
        return histories.stream()
                .map(CustomerHistoryMapper::toResponse)
                .toList();
    }

    static CustomerHistoryResponse toResponse(CustomerHistory history) {
        CustomerHistoryResponse response = new CustomerHistoryResponse();
        response.setCustomerHistoryId(history.getCustomerHistoryId());
        response.setCustomerId(history.getCustomer().getCustomerId());
        response.setVehicleId(history.getCustomerHistoryVehicle().getVehicleId());
        response.setCustomerHistoryReview(history.getCustomerHistoryReview());
        response.setDescription(history.getDescription());
        response.setActionDate(history.getActionDate());
        response.setForRentalCar(history.isForRentalCar());
        return response;
    }
}
