package com.acme.center.platform.flight.domain.services;

import com.acme.center.platform.flight.domain.model.commands.*;
import com.acme.center.platform.flight.domain.model.entities.Drone;

import java.util.Optional;

public interface DroneCommandService {

    Long handle(CreateDroneCommand command);
    //Optional<Drone> handle(UpdateDroneCommand command);
    void handle(DeleteDroneCommand command);

    //Optional<Drone> handle(AssignDroneToFlightRouteCommand command);
}
