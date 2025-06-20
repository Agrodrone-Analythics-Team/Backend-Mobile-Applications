package com.acme.center.platform.analitics.interfaces.rest.transform;

import com.acme.center.platform.analitics.domain.model.entities.AnimalDetection;
import com.acme.center.platform.analitics.interfaces.rest.resources.AnimalDetectionResource;

public class AnimalDetectionResourceFromEntityAssembler {

    public static AnimalDetectionResource toResourceFromEntity(AnimalDetection entity) {
        return new AnimalDetectionResource(
                entity.getFlightRouteId().value(),
                entity.getCount(),
                entity.getTimestamp()
        );
    }

}
