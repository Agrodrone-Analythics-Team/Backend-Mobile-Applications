package com.acme.center.platform.flight.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class GeoPoint {

    private double latitude;
    private double longitude;

    public GeoPoint() {}

    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}