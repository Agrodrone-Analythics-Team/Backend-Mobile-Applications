package com.acme.center.platform.flight.domain.model.valueobjects;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
public class FlightArea {
    @ElementCollection(fetch = FetchType.EAGER)
    private List<GeoPoint> polygon;

    public FlightArea() {
        this.polygon = new ArrayList<>();
    }

    public FlightArea(List<GeoPoint> polygon) {
        this.polygon = polygon;
    }
}
