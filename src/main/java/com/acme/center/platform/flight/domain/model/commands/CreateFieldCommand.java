package com.acme.center.platform.flight.domain.model.commands;

public record CreateFieldCommand (
        Long externalId,
        String name,
        double areaHectares,
        String cropType
) {}
