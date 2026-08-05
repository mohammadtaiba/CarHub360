package de.fherfurt.carhub360.customer.history;

import de.fherfurt.carhub360.shared.validation.RequiredText;
import jakarta.ejb.Stateless;
import java.util.Date;

@Stateless
public class CustomerHistoryValidator {

    public void validate(CustomerHistoryReview review, String description, Date actionDate) {
        if (review == null) {
            throw new IllegalArgumentException("customerHistoryReview is required.");
        }
        RequiredText.require(description, "description");
        if (actionDate == null) {
            throw new IllegalArgumentException("actionDate is required.");
        }
    }
}
