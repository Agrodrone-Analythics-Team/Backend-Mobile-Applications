package com.acme.center.platform.analitics.interfaces.rest.resources;

import java.time.LocalDateTime;

public record AnimalDetectionResource(
        Long flightRouteId,
        int count,
        LocalDateTime timestamp
) {}