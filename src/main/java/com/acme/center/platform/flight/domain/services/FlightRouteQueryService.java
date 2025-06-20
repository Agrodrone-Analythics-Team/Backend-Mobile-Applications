package com.acme.center.platform.flight.domain.services;

import com.acme.center.platform.flight.domain.model.aggregates.FlightRoute;
import com.acme.center.platform.flight.domain.model.queries.GetAllFlightRoutesQuery;
import com.acme.center.platform.flight.domain.model.queries.GetFlightRouteByIdQuery;
import com.acme.center.platform.flight.domain.model.valueobjects.FlightRouteStatus;

import java.util.List;
import java.util.Optional;

public interface FlightRouteQueryService {

    Optional<FlightRoute> handle(GetFlightRouteByIdQuery query);
    List<FlightRoute> handle(GetAllFlightRoutesQuery query);
    List<FlightRoute> handleByStatus(FlightRouteStatus status);
    //List<FlightRoute> handleByFieldId(Long fieldId);
    List<FlightRoute> handleByDroneId(Long droneId);

}