package com.acme.center.platform.analitics.interfaces.rest.transform;

import com.acme.center.platform.analitics.domain.model.commands.CreateAnimalDetectionCommand;
import com.acme.center.platform.analitics.interfaces.rest.resources.CreateAnimalDetectionResource;

public class CreateAnimalDetectionResourceFromEntityAssembler {
    public static CreateAnimalDetectionCommand toCommandFromResource(CreateAnimalDetectionResource resource) {
        return new CreateAnimalDetectionCommand(
                resource.flightRouteId(),
                resource.count(),
                resource.timestamp()
        );
    }
}
