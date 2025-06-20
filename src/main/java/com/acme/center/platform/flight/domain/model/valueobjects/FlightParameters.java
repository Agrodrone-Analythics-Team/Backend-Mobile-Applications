package com.acme.center.platform.flight.domain.model.valueobjects;


import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class FlightParameters {
    private double altitude;
    private double speed;

    public FlightParameters() {
        this.altitude = 0;
        this.speed = 0;
    }

    public FlightParameters(double altitude, double speed) {
        this.altitude = altitude;
        this.speed = speed;
    }
}
