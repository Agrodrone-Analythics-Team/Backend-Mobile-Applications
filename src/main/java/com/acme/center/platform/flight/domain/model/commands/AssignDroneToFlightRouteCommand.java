package com.acme.center.platform.flight.domain.model.commands;

public record AssignDroneToFlightRouteCommand(
    Long droneId,
    Long flightRouteId
) {}
