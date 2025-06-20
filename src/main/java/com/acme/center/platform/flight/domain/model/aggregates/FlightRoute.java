package com.acme.center.platform.flight.domain.model.aggregates;

import com.acme.center.platform.flight.domain.model.entities.Drone;
import com.acme.center.platform.flight.domain.model.entities.Field;
import com.acme.center.platform.flight.domain.model.valueobjects.*;
import com.acme.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
public class FlightRoute extends AuditableAbstractAggregateRoot<FlightRoute> {

    @Getter
    @Embedded
    @Column(name = "flight_route_id")
    private FlightRouteId flightRouteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drone_id")
    private Drone drone;

    @Embedded
    private FlightArea area;

    @Embedded
    private FlightLine line;

    @Embedded
    private FlightParameters parameters;

    @Enumerated(EnumType.STRING)
    @NotNull
    private FlightRouteStatus status;



    public FlightRoute() {
        this.status = FlightRouteStatus.PLANNED;
    }

    public FlightRoute(FlightArea area, FlightParameters parameters) {
        this();
        //this.field = field;
        this.area = area;
        this.parameters = parameters;
    }

    public FlightRoute(FlightLine line, FlightParameters parameters) {
        this();
        //this.field = field;
        this.line = line;
        this.parameters = parameters;
    }

    // ---------------- DOMAIN BEHAVIOR -------------------//
    public boolean isAreaRoute() {
        return area != null;
    }

    public boolean isLinearRoute() {
        return line != null;
    }

    public boolean canAssignDrone(Drone drone) {
        return this.status == FlightRouteStatus.PLANNED && drone != null && drone.isReadyToFly();
    }

    public boolean canStartFlight() {
        return this.status == FlightRouteStatus.ASSIGNED;
    }

    public boolean canCompleteFlight() {
        return this.status == FlightRouteStatus.IN_PROGRESS;
    }

    public void assignDrone(Drone drone) {
        if (!canAssignDrone(drone)) return;
        this.drone = drone;
        this.status = FlightRouteStatus.ASSIGNED;
    }

    public void startFlight() {
        if (!canStartFlight()) return;
        this.status = FlightRouteStatus.IN_PROGRESS;
    }

    public void completeFlight() {
        if (!canCompleteFlight()) return;
        this.status = FlightRouteStatus.COMPLETED;
    }

    public boolean isCompleted() {
        return this.status == FlightRouteStatus.COMPLETED;
    }

    public boolean isInProgress() {
        return this.status == FlightRouteStatus.IN_PROGRESS;
    }

    public boolean isPlanned() {
        return this.status == FlightRouteStatus.PLANNED;
    }
}