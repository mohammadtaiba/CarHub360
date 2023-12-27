package de.fherfurt;

/**
 * @author rudolfminz
 */
public class CustomerAddress {

    private int CustomerId;
    private String City;
    private String PostalCode;
    private String Street;
    private String StreetNumber;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getStreetNumber() {
        return StreetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        StreetNumber = streetNumber;
    }
    public void UpdateCustomerAddress(int CustomerId, int City, String PostalCode, String Street,String StreetNumber){

    }
}
