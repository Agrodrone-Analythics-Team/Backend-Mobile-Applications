package com.acme.center.platform.flight.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public enum FlightState {
    PLANNED, ASSIGNED, IN_PROGRESS, COMPLETED
}
