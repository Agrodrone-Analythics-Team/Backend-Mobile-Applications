package com.acme.center.platform.flight.interfaces.rest.resources;

public record AssignDroneToFlightRouteResource(
        long droneId,
        long flightRouteId) {
}
