package com.acme.center.platform.flight.interfaces.rest.resources;

public record CreateFlightLineResource(
        double startLat,
        double startLng,
        double endLat,
        double endLng
) {}
