package de.fherfurt;

/**
 * @author rudolfminz
 */
public class customer {

    private int CustomerId;
    private String FirstName;
    private String LastName;
    private String Email;
    private date Birthdate;
    private boolean IsFemale;

    public customer(int customerId, String firstName, String lastName, String email, date birthdate, boolean isFemale) {
        CustomerId = customerId;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Birthdate = birthdate;
        IsFemale = isFemale;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public date getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(date birthdate) {
        Birthdate = birthdate;
    }

    public boolean isFemale() {
        return IsFemale;
    }

    public void setFemale(boolean female) {
        IsFemale = female;
    }
    public void CreateCustomer(int CustomerId, String FirstName, String LastName, String Email, date Birthdate, boolean IsFemale){


    }
    public void DeleteCustomer(int CustomerID){

    }
    public void GetCustomerDetails(int CustomerID){

    }

}
