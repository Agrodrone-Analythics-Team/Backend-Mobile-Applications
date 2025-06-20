package com.acme.center.platform.flight.interfaces.rest.transform;

import com.acme.center.platform.flight.domain.model.commands.CreateDroneCommand;
import com.acme.center.platform.flight.domain.model.commands.CreateFlightRouteCommand;
import com.acme.center.platform.flight.interfaces.rest.resources.CreateDroneResource;
import com.acme.center.platform.flight.interfaces.rest.resources.CreateFlightRouteResource;

public class CreateDroneResourceFromEntityAssembler {
    public static CreateDroneCommand toCommandFromResource(CreateDroneResource resource) {
        return new CreateDroneCommand(
                resource.serialNumber(),
                resource.model(),
                resource.status()
        );
    }
}
