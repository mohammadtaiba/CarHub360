package de.fherfurt.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "maintenances")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maintenanceId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date maintenanceStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date maintenanceEndDate;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal maintenanceCost;

    @NotBlank
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String maintenanceDescription;

    public Maintenance() {
    }

    public Maintenance(int maintenanceId,
                       Vehicle vehicle,
                       Date maintenanceStartDate,
                       Date maintenanceEndDate,
                       BigDecimal maintenanceCost,
                       String maintenanceDescription) {
        this.maintenanceId = maintenanceId;
        this.vehicle = vehicle;
        this.maintenanceStartDate = maintenanceStartDate;
        this.maintenanceEndDate = maintenanceEndDate;
        this.maintenanceCost = maintenanceCost;
        this.maintenanceDescription = maintenanceDescription;
    }

    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getMaintenanceStartDate() {
        return maintenanceStartDate;
    }

    public void setMaintenanceStartDate(Date maintenanceStartDate) {
        this.maintenanceStartDate = maintenanceStartDate;
    }

    public Date getMaintenanceEndDate() {
        return maintenanceEndDate;
    }

    public void setMaintenanceEndDate(Date maintenanceEndDate) {
        this.maintenanceEndDate = maintenanceEndDate;
    }

    public BigDecimal getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(BigDecimal maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public String getMaintenanceDescription() {
        return maintenanceDescription;
    }

    public void setMaintenanceDescription(String maintenanceDescription) {
        this.maintenanceDescription = maintenanceDescription;
    }
}
