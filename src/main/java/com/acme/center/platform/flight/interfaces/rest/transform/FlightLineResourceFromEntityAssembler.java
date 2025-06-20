package com.acme.center.platform.flight.interfaces.rest.transform;

import com.acme.center.platform.flight.domain.model.valueobjects.FlightLine;
import com.acme.center.platform.flight.interfaces.rest.resources.FlightLineResource;

public class FlightLineResourceFromEntityAssembler {
    public static FlightLineResource toResourceFromEntity(FlightLine line) {
        return new FlightLineResource(
                line.getStartLat(),
                line.getStartLng(),
                line.getEndLat(),
                line.getEndLng()
        );
    }
}
