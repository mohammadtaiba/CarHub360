package de.fherfurt.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerAddressRequest {

    @NotBlank
    @Size(max = 120)
    private String city;

    @NotBlank
    @Size(max = 20)
    private String postalCode;

    @NotBlank
    @Size(max = 120)
    private String street;

    @NotBlank
    @Size(max = 20)
    private String streetNumber;

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
