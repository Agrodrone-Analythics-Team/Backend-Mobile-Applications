package com.acme.center.platform.flight.interfaces.rest.transform;

import com.acme.center.platform.flight.domain.model.aggregates.FlightRoute;
import com.acme.center.platform.flight.domain.model.entities.Drone;
import com.acme.center.platform.flight.interfaces.rest.resources.DroneResource;
import com.acme.center.platform.flight.interfaces.rest.resources.FlightRouteResource;

public class DroneResourceFromEntityAssembler {
    public static DroneResource toResourceFromEntity(Drone entity) {
        return new DroneResource(
                entity.getSerialNumber(),
                entity.getModel(),
                entity.getStatus()
        );

    }
}
