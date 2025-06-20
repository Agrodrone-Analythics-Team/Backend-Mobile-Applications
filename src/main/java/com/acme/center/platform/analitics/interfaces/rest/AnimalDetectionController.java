package com.acme.center.platform.analitics.interfaces.rest;

import com.acme.center.platform.analitics.domain.model.queries.GetAllAnimalDetectionsQuery;
import com.acme.center.platform.analitics.domain.model.queries.GetAnimalDetectionByIdQuery;
import com.acme.center.platform.analitics.domain.services.AnimalDetectionCommandService;
import com.acme.center.platform.analitics.domain.services.AnimalDetectionQueryService;
import com.acme.center.platform.analitics.interfaces.rest.resources.AnimalDetectionResource;
import com.acme.center.platform.analitics.interfaces.rest.resources.CreateAnimalDetectionResource;
import com.acme.center.platform.analitics.interfaces.rest.transform.AnimalDetectionResourceFromEntityAssembler;
import com.acme.center.platform.analitics.interfaces.rest.transform.CreateAnimalDetectionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/animal-detections", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Animal Detections", description = "Animal Detection Management Endpoints")
public class AnimalDetectionController {

    private final AnimalDetectionCommandService animalDetectionCommandService;
    private final AnimalDetectionQueryService animalDetectionQueryService;

    public AnimalDetectionController(AnimalDetectionCommandService animalDetectionCommandService, AnimalDetectionQueryService animalDetectionQueryService) {
        this.animalDetectionCommandService = animalDetectionCommandService;
        this.animalDetectionQueryService = animalDetectionQueryService;
    }

    @PostMapping
    public ResponseEntity<AnimalDetectionResource> createAnimalDetection(@RequestBody CreateAnimalDetectionResource resource) {
        var command = CreateAnimalDetectionResourceFromEntityAssembler.toCommandFromResource(resource);
        var animalDetectionId = animalDetectionCommandService.handle(command);
        if (animalDetectionId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getAnimalDetectionByIdQuery = new GetAnimalDetectionByIdQuery(animalDetectionId);
        var animalDetection = animalDetectionQueryService.handle(getAnimalDetectionByIdQuery);
        if (animalDetection.isEmpty()) return ResponseEntity.badRequest().build();
        var animalDetectionResource = AnimalDetectionResourceFromEntityAssembler.toResourceFromEntity(animalDetection.get());
        return new ResponseEntity<>(animalDetectionResource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDetectionResource> getById(@PathVariable Long id) {
        var getAnimalDetectionByIdQuery = new GetAnimalDetectionByIdQuery(id);
        var animalDetection = animalDetectionQueryService.handle(getAnimalDetectionByIdQuery);
        if (animalDetection.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(AnimalDetectionResourceFromEntityAssembler.toResourceFromEntity(animalDetection.get()));
    }

    @GetMapping
    public ResponseEntity<List<AnimalDetectionResource>> getAll() {
        var detections = animalDetectionQueryService.handle(new GetAllAnimalDetectionsQuery());
        var resources = detections.stream()
                .map(AnimalDetectionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}