package de.fherfurt.carhub360.shared.validation;

public final class RequiredText {

    private RequiredText() {
    }

    public static void require(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
    }
}
