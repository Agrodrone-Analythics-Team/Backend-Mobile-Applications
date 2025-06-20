package com.acme.center.platform.flight.domain.model.commands;

import com.acme.center.platform.flight.domain.model.valueobjects.FlightLine;
import com.acme.center.platform.flight.domain.model.valueobjects.GeoPoint;

import java.util.List;

public record CreateFlightRouteCommand(
        //Long fieldExternalId,
        //List<GeoPoint> areaPolygon,
        FlightLine flightLine,       // opcional
        double altitude,
        double speed
) { }
