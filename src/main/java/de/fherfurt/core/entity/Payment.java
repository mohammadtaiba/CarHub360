package de.fherfurt.core.entity;

import de.fherfurt.core.entity.utils.PaymentMethod;
import de.fherfurt.core.entity.utils.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a payment made by a customer, tracking payment details like ID, customer info,
 * payment method, status and amount.
 */
@Entity
@Table(name = "payments")
public class Payment {

    private static final BigDecimal MINIMUM_PAYMENT_AMOUNT = BigDecimal.ZERO;

    @Id
    private int paymentId;

    /**
     * Wir gehen davon aus, dass du in de.fherfurt.core.entity eine Customer-Entity hast.
     * Falls du die 1:1 übernehmen willst, kann hier @ManyToOne stehen.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /**
     * Achtung: Dieses Feld ist eigentlich redundant, wenn wir schon 'customer' haben.
     * Du kannst es beibehalten (z. B. für Logging) oder entfernen.
     */
    private int customerId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private BigDecimal paymentAmount;

    /**
     * In der Originalklasse gab es eine List<Payment> payments.
     * Das macht in JPA wenig Sinn, da es nicht erklärt, wie diese Liste mit Payment-Objekten verknüpft ist.
     * Wir markieren es als @Transient, damit JPA es ignoriert.
     */
    @Transient
    private List<Payment> payments = new ArrayList<>();

    /**
     * Parameterloser Konstruktor (für JPA erforderlich).
     */
    public Payment() {
    }

    /**
     * Creates a new Payment with the specified details.
     */
    public Payment(int paymentId,
                   Customer customer,
                   int customerId,
                   PaymentMethod paymentMethod,
                   PaymentStatus paymentStatus,
                   BigDecimal paymentAmount) {
        this.paymentId = paymentId;
        this.customer = customer;
        this.customerId = customerId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentAmount = paymentAmount;
    }

    // Getter und Setter

    public int getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }
    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
