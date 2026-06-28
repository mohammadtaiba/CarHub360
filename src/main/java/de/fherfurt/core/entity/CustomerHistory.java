package de.fherfurt.core.entity;

import de.fherfurt.core.entity.utils.CustomerHistoryReview;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "customer_histories")
public class CustomerHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerHistoryId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle customerHistoryVehicle;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CustomerHistoryReview customerHistoryReview;

    @NotBlank
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String description;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date actionDate;

    @Column(nullable = false)
    private boolean forRentalCar;

    public CustomerHistory() {
    }

    public CustomerHistory(int customerHistoryId,
                           Customer customer,
                           Vehicle customerHistoryVehicle,
                           CustomerHistoryReview customerHistoryReview,
                           String description,
                           Date actionDate,
                           boolean forRentalCar) {
        this.customerHistoryId = customerHistoryId;
        this.customer = customer;
        this.customerHistoryVehicle = customerHistoryVehicle;
        this.customerHistoryReview = customerHistoryReview;
        this.description = description;
        this.actionDate = actionDate;
        this.forRentalCar = forRentalCar;
    }

    public int getCustomerHistoryId() {
        return customerHistoryId;
    }

    public void setCustomerHistoryId(int customerHistoryId) {
        this.customerHistoryId = customerHistoryId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getCustomerHistoryVehicle() {
        return customerHistoryVehicle;
    }

    public void setCustomerHistoryVehicle(Vehicle customerHistoryVehicle) {
        this.customerHistoryVehicle = customerHistoryVehicle;
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
