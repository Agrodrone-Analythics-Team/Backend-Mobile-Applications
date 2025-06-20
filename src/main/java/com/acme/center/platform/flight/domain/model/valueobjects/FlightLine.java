package com.acme.center.platform.flight.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class FlightLine {

    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;

    public FlightLine() {}

    public FlightLine(double startLat, double startLng, double endLat, double endLng) {
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
    }
}