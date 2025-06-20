package com.acme.center.platform.analitics.domain.services;

import com.acme.center.platform.analitics.domain.model.commands.CreateAnimalDetectionCommand;

public interface AnimalDetectionCommandService {
    //void handle(RegisterAnimalDetectionsCommand command);
    //void handle(DeleteAnimalDetectionsByFlightRouteCommand command);
    //void handle(UpdateAnimalDetectionCommand command); // Opcional si hay edición
    Long handle(CreateAnimalDetectionCommand command);
}
