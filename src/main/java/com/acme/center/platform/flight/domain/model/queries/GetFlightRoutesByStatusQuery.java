package com.acme.center.platform.flight.domain.model.queries;

import com.acme.center.platform.flight.domain.model.valueobjects.FlightRouteStatus;

public record GetFlightRoutesByStatusQuery(FlightRouteStatus status) {
}
