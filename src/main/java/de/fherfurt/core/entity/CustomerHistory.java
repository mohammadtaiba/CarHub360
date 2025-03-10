package de.fherfurt.core.entity;

import de.fherfurt.core.entity.utils.CustomerHistoryReview;
import jakarta.persistence.*;
import java.util.Date;

/**
 * Represents a single customer history record, including details about actions,
 * vehicles, reviews, and other related information.
 */
@Entity
@Table(name = "customer_histories")
public class CustomerHistory {

    @Id
    private int customerHistoryId;

    /**
     * Wir gehen davon aus, dass in de.fherfurt.core.entity bereits eine Customer-Entity existiert.
     * Falls das Mapping 1:n ist, kannst du @ManyToOne verwenden.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /**
     * Ebenso für Vehicle. Falls du es 1:n haben willst, bleibe bei @ManyToOne.
     */
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle customerHistoryVehicle;

    /**
     * Enum für die Bewertung. Damit JPA den Enum-Wert als String speichert:
     */
    @Enumerated(EnumType.STRING)
    private CustomerHistoryReview customerHistoryReview;

    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;
    private boolean isForRentalCar;

    /**
     * Parameterloser Konstruktor (für JPA erforderlich).
     */
    public CustomerHistory() {
    }

    /**
     * Constructs a CustomerHistory object with the specified details.
     */
    public CustomerHistory(int customerHistoryId,
                           Customer customer,
                           Vehicle customerHistoryVehicle,
                           CustomerHistoryReview customerHistoryReview,
                           String description,
                           Date actionDate,
                           boolean isForRentalCar) {
        this.customerHistoryId = customerHistoryId;
        this.customer = customer;
        this.customerHistoryVehicle = customerHistoryVehicle;
        this.customerHistoryReview = customerHistoryReview;
        this.description = description;
        this.actionDate = actionDate;
        this.isForRentalCar = isForRentalCar;
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
        return isForRentalCar;
    }

    public void setForRentalCar(boolean forRentalCar) {
        isForRentalCar = forRentalCar;
    }
}
