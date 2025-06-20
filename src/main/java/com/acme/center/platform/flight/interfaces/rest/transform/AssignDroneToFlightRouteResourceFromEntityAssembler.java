package com.acme.center.platform.flight.interfaces.rest.transform;

import com.acme.center.platform.flight.domain.model.commands.AssignDroneToFlightRouteCommand;
import com.acme.center.platform.flight.interfaces.rest.resources.AssignDroneToFlightRouteResource;

public class AssignDroneToFlightRouteResourceFromEntityAssembler {
    public static AssignDroneToFlightRouteCommand toCommandFromResource(AssignDroneToFlightRouteResource resource) {
        return new AssignDroneToFlightRouteCommand(
                resource.droneId(),
                resource.flightRouteId()
        );
    }
}