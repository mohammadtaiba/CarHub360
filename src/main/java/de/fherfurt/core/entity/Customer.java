package de.fherfurt.core.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String firstName;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String lastName;

    @NotBlank
    @Email
    @Size(max = 160)
    @Column(nullable = false, unique = true, length = 160)
    private String email;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date birthdate;

    @Column(nullable = false)
    private boolean female;

    @Column(nullable = false)
    private boolean deleted;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private CustomerAddress customerAddress;

    public Customer() {
    }

    public Customer(int customerId,
                    String firstName,
                    String lastName,
                    String email,
                    Date birthdate,
                    boolean female,
                    CustomerAddress customerAddress) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.female = female;
        this.deleted = false;
        this.customerAddress = customerAddress;
    }

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

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
