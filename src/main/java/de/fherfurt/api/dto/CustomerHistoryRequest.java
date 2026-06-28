package de.fherfurt.api.dto;

import de.fherfurt.core.entity.utils.CustomerHistoryReview;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class CustomerHistoryRequest {

    @Positive
    private int customerId;

    @Positive
    private int vehicleId;

    @NotNull
    private CustomerHistoryReview customerHistoryReview;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotNull
    private Date actionDate;

    private boolean forRentalCar;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public CustomerHistoryReview getCustomerHistoryReview() {
        return customerHistoryReview;
    }

    public void setCustomerHistoryReview(CustomerHistoryReview customerHistoryReview) {
        this.customerHistoryReview = customerHistoryReview;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public boolean isForRentalCar() {
        return forRentalCar;
    }

    public void setForRentalCar(boolean forRentalCar) {
        this.forRentalCar = forRentalCar;
    }
}
