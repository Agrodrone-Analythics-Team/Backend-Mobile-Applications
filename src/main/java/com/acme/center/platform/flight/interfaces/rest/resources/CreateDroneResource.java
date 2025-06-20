package com.acme.center.platform.flight.interfaces.rest.resources;

import com.acme.center.platform.flight.domain.model.valueobjects.DroneStatus;

public record CreateDroneResource(
        String serialNumber,
        String model,
        DroneStatus status
) {
}
