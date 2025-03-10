package de.fherfurt.core.entity.utils;

/**
 * Represents the possible states of a payment in the system.
 * This enum is used to track the lifecycle of payments from initiation to completion.
 */
public enum PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED
}
