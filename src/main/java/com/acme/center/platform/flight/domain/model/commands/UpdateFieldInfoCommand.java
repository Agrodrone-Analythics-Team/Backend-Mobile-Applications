package com.acme.center.platform.flight.domain.model.commands;

public record UpdateFieldInfoCommand(
        Long fieldId,
        String name,
        double areaHectares,
        String cropType
) {}