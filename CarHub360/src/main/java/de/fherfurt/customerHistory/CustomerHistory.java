package de.fherfurt.customerHistory;

import de.fherfurt.customer.Customer;
import de.fherfurt.Vehicle.Vehicle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomerHistory {

    private static class CustomerHistoryDetails {
        private int customerHistoryId;
        private int customerHistoryCustomerId;
        private Vehicle customerHistoryVehicle;  // Assoziation mit Vehicle
        private CustomerHistoryReview customerHistoryReview;
        private String description;
        private Date actionDate;
        private boolean isForRentalCar;

        public CustomerHistoryDetails(int customerHistoryId, int customerHistoryCustomerId, Vehicle customerHistoryVehicle, CustomerHistoryReview customerHistoryReview, String description, Date actionDate, boolean isForRentalCar) {
            this.customerHistoryId = customerHistoryId;
            this.customerHistoryCustomerId = customerHistoryCustomerId;
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

        public int getCustomerHistoryCustomerId() {
            return customerHistoryCustomerId;
        }
        public void setCustomerHistoryCustomerId(int customerHistoryCustomerId) {
            this.customerHistoryCustomerId = customerHistoryCustomerId;
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

    public boolean createCustomerHistory(int customerHistoryId, int customerHistoryCustomerId,
                                         Vehicle customerHistoryVehicle, CustomerHistoryReview customerHistoryReview,
                                         String description, Date actionDate, boolean isForRentalCar) {
        if (customerHistoryId >= 0 && customerHistoryCustomerId >= 0 && customerHistoryVehicle != null
                && customerHistoryReview != null && description != null && actionDate != null) {

            CustomerHistoryDetails newCustomerHistory = new CustomerHistoryDetails(customerHistoryId,customerHistoryCustomerId,
            customerHistoryVehicle, customerHistoryReview,
                    description, actionDate, isForRentalCar);
            newCustomerHistory.setCustomerHistoryId(customerHistoryId);
            newCustomerHistory.setCustomerHistoryCustomerId(customerHistoryCustomerId);
            newCustomerHistory.setCustomerHistoryVehicle(customerHistoryVehicle);
            newCustomerHistory.setCustomerHistoryReview(customerHistoryReview);
            newCustomerHistory.setDescription(description);
            newCustomerHistory.setActionDate(actionDate);
            newCustomerHistory.setForRentalCar(isForRentalCar);

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
