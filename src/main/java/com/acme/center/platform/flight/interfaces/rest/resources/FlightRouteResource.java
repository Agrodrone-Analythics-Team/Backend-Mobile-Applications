package com.acme.center.platform.flight.interfaces.rest.resources;

public record FlightRouteResource(
                Long id,
                //String fieldName,
                Long droneId,
                String droneSerial,
                String status,
                String routeType, // "AREA" o "LINE"
                //List<GeoPointResource> areaPolygon,
                FlightLineResource flightLine,
                double altitude,
                double speed
) {}
