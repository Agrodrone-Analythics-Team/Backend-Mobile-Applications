package com.acme.center.platform.flight.interfaces.rest.resources;

public record FlightLineResource(
        double startLat,
        double startLng,
        double endLat,
        double endLng
) {
}
