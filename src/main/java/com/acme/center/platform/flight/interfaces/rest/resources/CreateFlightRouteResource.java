package com.acme.center.platform.flight.interfaces.rest.resources;

import com.acme.center.platform.flight.domain.model.valueobjects.FlightLine;

public record CreateFlightRouteResource(
        //long fieldExternalId,
        FlightLine flightLine,
        double altitude,
        double speed
) {}
