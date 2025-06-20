package com.acme.center.platform.analitics.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.List;

public record RegisterAnimalDetectionsResource(
        String flightRouteId,
        List<CreateAnimalDetectionResource> detections,
        LocalDateTime timestamp
) {
}
