package com.acme.center.platform.flight.domain.model.entities;

import com.acme.center.platform.flight.domain.model.commands.CreateDroneCommand;
import com.acme.center.platform.flight.domain.model.commands.CreateFlightRouteCommand;
import com.acme.center.platform.flight.domain.model.valueobjects.DroneStatus;
import com.acme.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.center.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
public class Drone extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String serialNumber;

    @NotNull
    private String model;

    @NotNull
    private double batteryLevel;

    @Enumerated(EnumType.STRING)
    @NotNull
    private DroneStatus status;

    @NotNull
    private boolean cameraOperational;

    @NotNull
    private boolean gpsOperational;

    public Drone() {
        this.status = DroneStatus.IDLE;
        this.batteryLevel = 100.0;
        this.cameraOperational = true;
        this.gpsOperational = true;
    }

    public Drone(CreateDroneCommand command) {
        this.serialNumber = command.serialNumber();
        this.model = command.model();
        this.status = DroneStatus.IDLE;
        this.batteryLevel = 100.0; // Default battery level
        this.cameraOperational = true;
        this.gpsOperational = true;
    }

    public boolean isReadyToFly() {
        return this.batteryLevel > 20.0 && this.cameraOperational && this.gpsOperational && this.status == DroneStatus.IDLE;
    }

    public void assignToRoute() {
        this.status = DroneStatus.ASSIGNED;
    }

    public void startFlight() {
        this.status = DroneStatus.IN_FLIGHT;
    }

    public void completeFlight() {
        this.status = DroneStatus.IDLE;
    }

    public void markAsUnavailable() {
        this.status = DroneStatus.UNAVAILABLE;
    }

    public void updateBatteryLevel(double newLevel) {
        this.batteryLevel = Math.max(0.0, Math.min(newLevel, 100.0));
    }

    public void updateSensorStatus(boolean cameraOk, boolean gpsOk) {
        this.cameraOperational = cameraOk;
        this.gpsOperational = gpsOk;
    }
}
