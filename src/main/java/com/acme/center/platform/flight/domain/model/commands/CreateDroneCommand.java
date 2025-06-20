package com.acme.center.platform.flight.domain.model.commands;

import com.acme.center.platform.flight.domain.model.valueobjects.DroneStatus;

public record CreateDroneCommand(
        String serialNumber,
        String model,
        DroneStatus status
) {}
