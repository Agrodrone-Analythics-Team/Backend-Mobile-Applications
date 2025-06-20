package com.acme.center.platform.analitics.interfaces.rest.resources;

import com.acme.center.platform.flight.domain.model.valueobjects.FlightRouteId;

import java.time.LocalDateTime;

public record CreateAnimalDetectionResource(
        FlightRouteId flightRouteId,
        int count,
        LocalDateTime timestamp) {}


