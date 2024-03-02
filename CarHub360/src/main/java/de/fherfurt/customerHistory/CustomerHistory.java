package de.fherfurt.customerHistory;

import de.fherfurt.customer.Customer;
import de.fherfurt.Vehicle.Vehicle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomerHistory {

    private static class CustomerHistoryDetails {
        private int customerHistoryId;
        private Customer customer;
        private Vehicle customerHistoryVehicle;
        private CustomerHistoryReview customerHistoryReview;
        private String description;
        private Date actionDate;
        private boolean isForRentalCar;

        public CustomerHistoryDetails(int customerHistoryId, Customer customer, Vehicle customerHistoryVehicle, CustomerHistoryReview customerHistoryReview, String description, Date actionDate, boolean isForRentalCar) {
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

    private Map<Integer, CustomerHistoryDetails> customerHistoryMap = new HashMap<>();

    public boolean createCustomerHistory(int customerHistoryId, Customer customer,
                                         Vehicle customerHistoryVehicle, CustomerHistoryReview customerHistoryReview,
                                         String description, Date actionDate, boolean isForRentalCar) {
        if (customerHistoryId >= 0 && customer != null && customerHistoryVehicle != null
                && customerHistoryReview != null && description != null && actionDate != null) {

            CustomerHistoryDetails newCustomerHistory = new CustomerHistoryDetails(customerHistoryId, customer,
                    customerHistoryVehicle, customerHistoryReview,
                    description, actionDate, isForRentalCar);

            customerHistoryMap.put(customerHistoryId, newCustomerHistory);
            return true;
        } else {
            return false;
        }
    }

    public CustomerHistoryDetails getCustomerHistory(int customerHistoryId) {
        return customerHistoryMap.get(customerHistoryId);
    }

    public CustomerHistoryReview getCustomerFinalReview(int customerHistoryId) {
        CustomerHistoryDetails customerHistoryDetails = customerHistoryMap.get(customerHistoryId);
        if (customerHistoryDetails != null) {
            return customerHistoryDetails.getCustomerHistoryReview();
        } else {
            return null;
        }
    }
}
