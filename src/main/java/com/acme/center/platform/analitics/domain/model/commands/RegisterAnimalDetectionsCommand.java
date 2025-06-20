package com.acme.center.platform.analitics.domain.model.commands;

import com.acme.center.platform.flight.domain.model.valueobjects.FlightRouteId;

import java.time.LocalDateTime;

public record RegisterAnimalDetectionsCommand (
        FlightRouteId flightRouteId,
        //List<AnimalDetection> detections,
        LocalDateTime timestamp
){}
