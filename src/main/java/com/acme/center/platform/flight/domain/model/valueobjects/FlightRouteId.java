package com.acme.center.platform.flight.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record FlightRouteId(Long value) {
    public FlightRouteId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("FlightRouteId must be a positive number");
        }
    }

    public FlightRouteId() {
        this(0L); // Solo si tu ORM necesita un constructor sin args
    }
}
