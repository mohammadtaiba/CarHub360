package de.fherfurt.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class CustomerRequest {

    @NotBlank
    @Size(max = 80)
    private String firstName;

    @NotBlank
    @Size(max = 80)
    private String lastName;

    @NotBlank
    @Email
    @Size(max = 160)
    private String email;

    @NotNull
    private Date birthdate;

    @NotNull
    private Boolean female;

    @Valid
    private CustomerAddressRequest address;

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

    public Boolean getFemale() {
        return female;
    }

    public void setFemale(Boolean female) {
        this.female = female;
    }

    public CustomerAddressRequest getAddress() {
        return address;
    }

    public void setAddress(CustomerAddressRequest address) {
        this.address = address;
    }
}
