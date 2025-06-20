package com.acme.center.platform.flight.interfaces.rest;

import com.acme.center.platform.flight.domain.model.queries.GetAllDronesQuery;
import com.acme.center.platform.flight.domain.model.queries.GetDroneByIdQuery;
import com.acme.center.platform.flight.domain.services.DroneCommandService;
import com.acme.center.platform.flight.domain.services.DroneQueryService;
import com.acme.center.platform.flight.interfaces.rest.resources.CreateDroneResource;
import com.acme.center.platform.flight.interfaces.rest.resources.DroneResource;
import com.acme.center.platform.flight.interfaces.rest.transform.CreateDroneResourceFromEntityAssembler;
import com.acme.center.platform.flight.interfaces.rest.transform.DroneResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/drones", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Drone", description = "Drone Management Endpoints")
public class DroneController {

    private final DroneCommandService droneCommandService;
    private final DroneQueryService droneQueryService;

    public DroneController(
            DroneCommandService droneCommandService,
            DroneQueryService droneQueryService
    ) {
        this.droneCommandService = droneCommandService;
        this.droneQueryService = droneQueryService;
    }

    @PostMapping
    public ResponseEntity<DroneResource> createDrone(@RequestBody CreateDroneResource createDroneResource) {
        var createDroneCommand = CreateDroneResourceFromEntityAssembler.toCommandFromResource(createDroneResource);
        var droneId = droneCommandService.handle(createDroneCommand);
        if (droneId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getDroneByIdQuery = new GetDroneByIdQuery(droneId);
        var drone = droneQueryService.handle(getDroneByIdQuery);
        if (drone.isEmpty()) return ResponseEntity.badRequest().build();
        var droneResource = DroneResourceFromEntityAssembler.toResourceFromEntity(drone.get());
        return new ResponseEntity<>(droneResource, HttpStatus.CREATED);
    }

    @GetMapping("/{droneId}")
    public ResponseEntity<DroneResource> getDroneById(@PathVariable Long droneId) {
        var getDroneByIdQuery = new GetDroneByIdQuery(droneId);
        var drone = droneQueryService.handle(getDroneByIdQuery);
        if (drone.isEmpty()) return ResponseEntity.badRequest().build();
        var droneResource = DroneResourceFromEntityAssembler.toResourceFromEntity(drone.get());
        return ResponseEntity.ok(droneResource);
    }

    @GetMapping
    public ResponseEntity<List<DroneResource>> getAllDrones() {
        var getAllDronesQuery = new GetAllDronesQuery();
        var drones = droneQueryService.handle(getAllDronesQuery);
        var dronesResources = drones.stream().map(DroneResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(dronesResources);
    }




}
