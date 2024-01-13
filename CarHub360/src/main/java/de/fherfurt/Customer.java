package de.fherfurt;
import de.fherfurt.contract.Contract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Customer {

    private class CustomerDetails {


        private int CustomerId;
        private String FirstName;
        private String LastName;
        private String Email;
        private Date Birthdate;
        private boolean IsFemale;
        private boolean IsDeleted;

        public CustomerDetails(int CustomerId, String firstName, String lastName, String email, Date birthdate, boolean isFemale) {
            this.CustomerId = CustomerId;
            this.FirstName = firstName;
            this.LastName = lastName;
            this.Email = email;
            this.Birthdate = birthdate;
            this.IsFemale = isFemale;
            this.IsDeleted = false;
        }

        public int getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(int CustomerId) {
            CustomerId = CustomerId;
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

        public Date getBirthdate() {
            return Birthdate;
        }

        public void setBirthdate(Date birthdate) {
            Birthdate = birthdate;
        }

        public boolean isFemale() {
            return IsFemale;
        }

        public void setFemale(boolean female) {
            IsFemale = female;
        }

        public boolean isDeleted() {
            return IsDeleted;
        }

        public void setDelete(boolean deleted) {
            IsDeleted = deleted;
        }

    }

    private Map<Integer, Customer.CustomerDetails> customers = new HashMap<>();

    public boolean CreateCustomer(int CustomerId, String FirstName, String LastName, String Email, Date Birthdate, boolean IsFemale) {

        if (!customers.containsKey(CustomerId)) {
            if (CustomerId >= 0 && FirstName != null && LastName != null && Email != null && Birthdate != null) {
                Customer.CustomerDetails newCustomer = new Customer.CustomerDetails(CustomerId, FirstName, LastName, Email, Birthdate, IsFemale);

                return true;
            } else {
                System.out.println("Invalid customer details. Customer not created.");
                return false;
            }

        } else {
            System.out.println("CustomerId is taken");
            return false;
        }

    }


    public boolean DeleteCustomer(int CustomerId) {
        if (customers.containsKey(CustomerId)) {
            CustomerDetails customer = customers.get(CustomerId);
            customer.setDelete(true);
            System.out.println("Customer with ID " + CustomerId + " deleted.");
            return true;
        } else {
            System.out.println("Customer with ID " + CustomerId + " not found.");
            return false;
        }
    }
    public String getCustomerDetails(int CustomerId) {
        if (customers.containsKey(CustomerId)) {
            CustomerDetails customer = customers.get(CustomerId);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String details = "Customer details for ID " + CustomerId + ":\n";
            details += "Name: " + customer.getFirstName() + " " + customer.getLastName() + "\n";
            details += "Email: " + customer.getEmail() + "\n";
            details += "Birthdate: " + dateFormat.format(customer.getBirthdate()) + "\n";
            details += "IsFemale: " + customer.isFemale() + "\n";
            details += "IsDeleted: " + customer.isDeleted() + "\n";

            return details;
        } else {
            return "Customer with ID " + CustomerId + " not found.";
        }
    }
}




