package com.acme.center.platform.analitics.domain.model.queries;

import com.acme.center.platform.flight.domain.model.valueobjects.FlightRouteId;

public record GetAnimalDetectionsByFlightRouteQuery(FlightRouteId flightRouteId) {}
