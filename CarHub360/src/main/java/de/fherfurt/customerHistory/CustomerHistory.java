package de.fherfurt.customerHistory;
import de.fherfurt.customer.Customer;

import java.util.Date;

public class CustomerHistory {
    private int CustomerHistoryId;
    private int CustomerHistoryCustomerId;
    private int CustomerHistoryVehicleId;
    private CustomerHistoryReview CustomerHistoryReview;
    private String Description;
    private Date ActionDate;
    private boolean IsforRentalCar;

    public int getCustomerHistoryId() {
        return CustomerHistoryId;
    }

    public void setCustomerHistoryId(int customerHistoryId) {
        CustomerHistoryId = customerHistoryId;
    }

    public int getCustomerHistoryCustomerId() {
        return CustomerHistoryCustomerId;
    }

    public void setCustomerHistoryCustomerId(int customerHistoryCustomerId) {
        CustomerHistoryCustomerId = customerHistoryCustomerId;
    }

    public int getCustomerHistoryVehicleId() {
        return CustomerHistoryVehicleId;
    }

    public void setCustomerHistoryVehicleId(int customerHistoryVehicleId) {
        CustomerHistoryVehicleId = customerHistoryVehicleId;
    }

    public de.fherfurt.customerHistory.CustomerHistoryReview getCustomerHistoryReview() {
        return CustomerHistoryReview;
    }

    public void setCustomerHistoryReview(de.fherfurt.customerHistory.CustomerHistoryReview customerHistoryReview) {
        CustomerHistoryReview = customerHistoryReview;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getActionDate() {
        return ActionDate;
    }

    public void setActionDate(Date actionDate) {
        ActionDate = actionDate;
    }

    public boolean isIsforRentalCar() {
        return IsforRentalCar;
    }

    public void setIsforRentalCar(boolean isforRentalCar) {
        IsforRentalCar = isforRentalCar;
    }

    // Ergänzung für Aggregation mit Customer
    private Customer.CustomerDetails relatedCustomerDetails;

    public CustomerHistory(int customerHistoryId, int customerHistoryCustomerId, int customerHistoryVehicleId,
                           CustomerHistoryReview customerHistoryReview, String description, Date actionDate,
                           boolean isforRentalCar, Customer.CustomerDetails relatedCustomerDetails) {
        this.CustomerHistoryId = customerHistoryId;
        this.CustomerHistoryCustomerId = customerHistoryCustomerId;
        this.CustomerHistoryVehicleId = customerHistoryVehicleId;
        this.CustomerHistoryReview = customerHistoryReview;
        this.Description = description;
        this.ActionDate = actionDate;
        this.IsforRentalCar = isforRentalCar;
        this.relatedCustomerDetails = relatedCustomerDetails;
    }

    // Methode zur Abfrage der verbundenen Kundeninformationen
    public Customer.CustomerDetails getRelatedCustomerDetails() {
        return this.relatedCustomerDetails;
    }

    // Weitere Methoden können hier hinzugefügt werden, um die CustomerHistory zu verwalten.

    // Ergänzung für die Vervollständigung der CreateCustomerHistory-Methode
    public boolean CreateCustomerHistory(int customerHistoryId, int customerHistoryCustomerId,
                                         int customerHistoryVehicleId, CustomerHistoryReview customerHistoryReview,
                                         String description, Date actionDate, boolean isforRentalCar,
                                         Customer.CustomerDetails relatedCustomerDetails) {
        if (customerHistoryId >= 0 && customerHistoryCustomerId >= 0 && customerHistoryVehicleId >= 0
                && customerHistoryReview != null && description != null && actionDate != null) {
            this.CustomerHistoryId = customerHistoryId;
            this.CustomerHistoryCustomerId = customerHistoryCustomerId;
            this.CustomerHistoryVehicleId = customerHistoryVehicleId;
            this.CustomerHistoryReview = customerHistoryReview;
            this.Description = description;
            this.ActionDate = actionDate;
            this.IsforRentalCar = isforRentalCar;
            this.relatedCustomerDetails = relatedCustomerDetails;
            // Additional logic or storage (if needed) can be added here
            return true;
        } else {
            return false;
        }
    }

    // Vervollständigung der GetCustomerHistory-Methode
    public CustomerHistory GetCustomerHistory(int customerHistoryId) {
        if (this.CustomerHistoryId == customerHistoryId) {
            return this;
        } else {
            return null; // Not found
        }
    }

    // Vervollständigung der GetCustomerFinalReview-Methode
    public CustomerHistoryReview GetCustomerFinalReview() {
        return this.CustomerHistoryReview;
    }
}
