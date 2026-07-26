package de.fherfurt.carhub360.customer.dto;

import java.util.Date;

public class CustomerResponse {

    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthdate;
    private boolean female;
    private boolean deleted;
    private CustomerAddressResponse customerAddress;
    private String fullName;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public CustomerAddressResponse getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddressResponse customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
