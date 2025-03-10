package de.fherfurt.core.entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Represents a customer entity with personal information and address details.
 */
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    private int customerId;

    private String firstName;
    private String lastName;
    private String email;
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    private boolean isFemale;
    private boolean isDeleted;

    /**
     * Wir gehen davon aus, dass auch CustomerAddress eine JPA-Entität ist.
     * Ggf. @ManyToOne oder @OneToOne je nach gewünschter Beziehung.
     * Hier als Beispiel @ManyToOne.
     */
    @ManyToOne
    @JoinColumn(name = "address_id")
    private CustomerAddress customerAddress;

    /**
     * Parameterloser Konstruktor (für JPA erforderlich).
     */
    public Customer() {
    }

    /**
     * Creates a new Customer with the specified details.
     */
    public Customer(int customerId, String firstName, String lastName, String email,
                    Date birthdate, boolean isFemale, CustomerAddress customerAddress) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.isFemale = isFemale;
        this.isDeleted = false;
        this.customerAddress = customerAddress;
    }

    // Getter und Setter
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
        return isFemale;
    }
    public void setFemale(boolean female) {
        isFemale = female;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }
    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getDetails() {
        return "Customer ID: " + customerId
                + ", Name: " + firstName + " " + lastName
                + ", Email: " + email;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
