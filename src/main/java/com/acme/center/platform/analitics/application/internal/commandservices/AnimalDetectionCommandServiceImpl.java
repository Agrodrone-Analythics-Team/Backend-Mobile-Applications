package com.acme.center.platform.analitics.application.internal.commandservices;

import com.acme.center.platform.analitics.domain.model.commands.CreateAnimalDetectionCommand;
import com.acme.center.platform.analitics.domain.model.entities.AnimalDetection;
import com.acme.center.platform.analitics.domain.services.AnimalDetectionCommandService;
import com.acme.center.platform.analitics.infrastructure.persistence.jpa.repositories.AnimalDetectionRepository;
import org.springframework.stereotype.Service;

@Service
public class AnimalDetectionCommandServiceImpl implements AnimalDetectionCommandService {

    private final AnimalDetectionRepository animalDetectionRepository;

    public AnimalDetectionCommandServiceImpl (
            AnimalDetectionRepository animalDetectionRepository
    )
    {
        this.animalDetectionRepository = animalDetectionRepository;
    }


    @Override
    public Long handle(CreateAnimalDetectionCommand command) {
        var detection = new AnimalDetection(
                command.flightRouteId(),
                command.count(),
                command.timestamp()
        );

        try {
            var saved = animalDetectionRepository.save(detection);
            return saved.getId();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving animal detection: " + e.getMessage());
        }
    }
}