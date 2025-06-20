package com.acme.center.platform.analitics.domain.model.commands;

import com.acme.center.platform.flight.domain.model.valueobjects.FlightRouteId;

import java.time.LocalDateTime;

public record CreateAnimalDetectionCommand(    FlightRouteId flightRouteId,
                                               int count,
                                               LocalDateTime timestamp){
}
