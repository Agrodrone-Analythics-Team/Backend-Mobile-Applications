package com.acme.center.platform.flight.domain.model.commands;

public record StartFlightRouteCommand(
        Long flightRouteId,
        Long droneId
) {}
