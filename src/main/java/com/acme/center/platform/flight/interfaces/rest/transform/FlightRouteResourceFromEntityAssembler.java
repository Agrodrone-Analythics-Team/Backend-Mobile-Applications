package com.acme.center.platform.flight.interfaces.rest.transform;

import com.acme.center.platform.flight.domain.model.aggregates.FlightRoute;
import com.acme.center.platform.flight.interfaces.rest.resources.FlightLineResource;
import com.acme.center.platform.flight.interfaces.rest.resources.FlightRouteResource;

public class FlightRouteResourceFromEntityAssembler {

    public static FlightRouteResource toResourceFromEntity(FlightRoute entity) {
        return new FlightRouteResource(
                entity.getId(),
                //entity.getField().getName(),
                entity.getDrone() != null ? entity.getDrone().getId() : null,
                entity.getDrone() != null ? entity.getDrone().getSerialNumber() : null,
                entity.getStatus().name(),
                entity.isLinearRoute() ? "LINE" : "AREA",
                //entity.getArea() != null ?  : null,
                entity.getLine() != null
                        ? FlightLineResourceFromEntityAssembler.toResourceFromEntity(entity.getLine())
                        : null,
                entity.getParameters().getAltitude(),
                entity.getParameters().getSpeed()
        );

    }
}
