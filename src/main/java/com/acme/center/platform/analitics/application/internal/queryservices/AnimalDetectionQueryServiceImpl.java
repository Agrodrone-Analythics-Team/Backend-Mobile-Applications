package com.acme.center.platform.analitics.application.internal.queryservices;

import com.acme.center.platform.analitics.domain.model.entities.AnimalDetection;
import com.acme.center.platform.analitics.domain.model.queries.*;
import com.acme.center.platform.analitics.domain.model.valueobjects.AnimalDetectionSummary;
import com.acme.center.platform.analitics.domain.services.AnimalDetectionQueryService;
import com.acme.center.platform.analitics.infrastructure.persistence.jpa.repositories.AnimalDetectionRepository;
import com.acme.center.platform.flight.domain.model.valueobjects.FlightRouteId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalDetectionQueryServiceImpl implements AnimalDetectionQueryService {

    private final AnimalDetectionRepository animalDetectionRepository;

    public AnimalDetectionQueryServiceImpl(AnimalDetectionRepository animalDetectionRepository) {
        this.animalDetectionRepository = animalDetectionRepository;
    }

    @Override
    public List<AnimalDetection> handle(GetAllAnimalDetectionsQuery query) {
        return List.of();
    }

    @Override
    public List<AnimalDetection> handle(GetAnimalDetectionsByFlightRouteQuery query) {
        var routeId = new FlightRouteId(Long.parseLong(String.valueOf(query.flightRouteId())));
        return animalDetectionRepository.findAllByFlightRouteId(routeId);
    }

    @Override
    public List<AnimalDetectionSummary> handle(GetAnimalDetectionSummaryByFlightRouteQuery query) {
        return List.of();
    }

    @Override
    public List<AnimalDetection> handle(GetAnimalDetectionsBySpeciesQuery query) {
        return List.of();
    }

    @Override
    public Optional<AnimalDetection> handle(GetAnimalDetectionByIdQuery query) {
        return animalDetectionRepository.findById(query.id());
    }
}