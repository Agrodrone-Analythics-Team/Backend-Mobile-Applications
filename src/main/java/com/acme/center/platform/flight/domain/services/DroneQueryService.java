package com.acme.center.platform.flight.domain.services;

import com.acme.center.platform.flight.domain.model.aggregates.FlightRoute;
import com.acme.center.platform.flight.domain.model.entities.Drone;
import com.acme.center.platform.flight.domain.model.queries.GetAllDronesQuery;
import com.acme.center.platform.flight.domain.model.queries.GetDroneByIdQuery;


import java.util.List;
import java.util.Optional;

public interface DroneQueryService {
    Optional<Drone> handle(GetDroneByIdQuery query);
    List<Drone> handle(GetAllDronesQuery query);
}
