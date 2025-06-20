package com.acme.center.platform.analitics.domain.services;

import com.acme.center.platform.analitics.domain.model.entities.AnimalDetection;
import com.acme.center.platform.analitics.domain.model.queries.*;
import com.acme.center.platform.analitics.domain.model.valueobjects.AnimalDetectionSummary;

import java.util.List;
import java.util.Optional;

public interface AnimalDetectionQueryService {
    List<AnimalDetection> handle(GetAllAnimalDetectionsQuery query);

    List<AnimalDetection> handle(GetAnimalDetectionsByFlightRouteQuery query);

    List<AnimalDetectionSummary> handle(GetAnimalDetectionSummaryByFlightRouteQuery query);

    List<AnimalDetection> handle(GetAnimalDetectionsBySpeciesQuery query);

    Optional<AnimalDetection> handle(GetAnimalDetectionByIdQuery query);
}
