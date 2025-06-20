package com.acme.center.platform.flight.domain.model.commands;

public record CompleteFlightRouteCommand(
    Long flightRouteId,
    Long droneId
) {}
