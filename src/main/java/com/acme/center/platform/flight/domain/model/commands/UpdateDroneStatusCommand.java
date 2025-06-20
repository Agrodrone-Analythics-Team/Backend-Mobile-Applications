package com.acme.center.platform.flight.domain.model.commands;

import com.acme.center.platform.flight.domain.model.valueobjects.DroneStatus;

public record UpdateDroneStatusCommand(
        long DroneId,
        DroneStatus status
) {}
