package com.acme.center.platform.flight.domain.services;

import com.acme.center.platform.flight.domain.model.aggregates.FlightRoute;
import com.acme.center.platform.flight.domain.model.commands.*;

import java.util.Optional;

public interface FlightRouteCommandService {
    Long handle(CreateFlightRouteCommand command);
    Optional<FlightRoute> handle(AssignDroneToFlightRouteCommand command);
    Optional<FlightRoute> handle(StartFlightRouteCommand command);
    Optional<FlightRoute> handle(CompleteFlightRouteCommand command);
    void handle(DeleteFlightRouteCommand command);
}