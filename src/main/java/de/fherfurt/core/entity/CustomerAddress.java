package de.fherfurt.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a customer's address with attributes like customer ID, city, postal code, street, and street number.
 */
@Entity
@Table(name = "customer_addresses")
public class CustomerAddress {

    @Id
    private int customerId;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;

    /**
     * Parameterloser Konstruktor (für JPA erforderlich).
     */
    public CustomerAddress() {
    }

    /**
     * Constructs a CustomerAddress object with the provided details.
     *
     * @param customerId   Customer's unique identifier
     * @param city         Customer's city
     * @param postalCode   Customer's postal code
     * @param street       Customer's street name
     * @param streetNumber Customer's street number
     */
    public CustomerAddress(int customerId, String city, String postalCode, String street, String streetNumber) {
        this.customerId = customerId;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
}
