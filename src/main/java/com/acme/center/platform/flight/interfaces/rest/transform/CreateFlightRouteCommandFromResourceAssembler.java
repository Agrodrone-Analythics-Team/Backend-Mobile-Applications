package com.acme.center.platform.flight.interfaces.rest.transform;

import com.acme.center.platform.flight.domain.model.commands.CreateFlightRouteCommand;
import com.acme.center.platform.flight.interfaces.rest.resources.CreateFlightRouteResource;

public class CreateFlightRouteCommandFromResourceAssembler {
    public static CreateFlightRouteCommand toCommandFromResource(CreateFlightRouteResource resource) {
        return new CreateFlightRouteCommand(
                //resource.fieldExternalId(),
                resource.flightLine(),
                resource.altitude(),
                resource.speed()
        );
    }
}