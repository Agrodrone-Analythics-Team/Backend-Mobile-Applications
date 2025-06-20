package com.acme.center.platform.flight.interfaces.rest.transform;

import com.acme.center.platform.flight.domain.model.valueobjects.FlightLine;
import com.acme.center.platform.flight.interfaces.rest.resources.CreateFlightLineResource;

public class CreateFlightLineResourceFromEntityAssembler {

    public static CreateFlightLineResource toResourceFromEntity(FlightLine line) {
        return new CreateFlightLineResource(
                line.getStartLat(),
                line.getStartLng(),
                line.getEndLat(),
                line.getEndLng()
        );
    }
}