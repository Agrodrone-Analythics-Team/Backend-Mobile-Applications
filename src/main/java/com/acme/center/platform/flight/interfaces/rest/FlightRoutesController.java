package com.acme.center.platform.flight.interfaces.rest;

import com.acme.center.platform.flight.domain.model.commands.DeleteFlightRouteCommand;
import com.acme.center.platform.flight.domain.model.queries.GetAllFlightRoutesQuery;
import com.acme.center.platform.flight.domain.model.queries.GetFlightRouteByIdQuery;
import com.acme.center.platform.flight.domain.services.FlightRouteCommandService;
import com.acme.center.platform.flight.domain.services.FlightRouteQueryService;
import com.acme.center.platform.flight.interfaces.rest.resources.AssignDroneToFlightRouteResource;
import com.acme.center.platform.flight.interfaces.rest.resources.CreateFlightRouteResource;
import com.acme.center.platform.flight.interfaces.rest.resources.FlightRouteResource;
import com.acme.center.platform.flight.interfaces.rest.transform.AssignDroneToFlightRouteResourceFromEntityAssembler;
import com.acme.center.platform.flight.interfaces.rest.transform.CreateFlightRouteCommandFromResourceAssembler;
import com.acme.center.platform.flight.interfaces.rest.transform.FlightRouteResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/flight-routes", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Flight Routes", description = "Flight Route Management Endpoints")
public class FlightRoutesController {

    private final FlightRouteCommandService flightRouteCommandService;
    private final FlightRouteQueryService flightRouteQueryService;

    public FlightRoutesController(
            FlightRouteCommandService flightRouteCommandService,
            FlightRouteQueryService flightRouteQueryService
    ) {
        this.flightRouteCommandService = flightRouteCommandService;
        this.flightRouteQueryService = flightRouteQueryService;
    }

    @PostMapping
    public ResponseEntity<FlightRouteResource> createFlightRoute(@RequestBody CreateFlightRouteResource createFlightRouteResource) {
        var createFlightRouteCommand = CreateFlightRouteCommandFromResourceAssembler.toCommandFromResource(createFlightRouteResource);
        var flightId = flightRouteCommandService.handle(createFlightRouteCommand);
        if (flightId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getFlightRouteByIdQuery = new GetFlightRouteByIdQuery(flightId);
        var flightRoute = flightRouteQueryService.handle(getFlightRouteByIdQuery);
        if (flightRoute.isEmpty()) return ResponseEntity.badRequest().build();
        var flightRouteResource = FlightRouteResourceFromEntityAssembler.toResourceFromEntity(flightRoute.get());
        return new ResponseEntity<>(flightRouteResource, HttpStatus.CREATED);
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<FlightRouteResource> getFlightRouteById(@PathVariable Long routeId) {
        var getFlightRouteByIdQuery = new GetFlightRouteByIdQuery(routeId);
        var flightRoute = flightRouteQueryService.handle(getFlightRouteByIdQuery);
        if (flightRoute.isEmpty()) return ResponseEntity.badRequest().build();
        var flightRouteResource = FlightRouteResourceFromEntityAssembler.toResourceFromEntity(flightRoute.get());
        return ResponseEntity.ok(flightRouteResource);
    }

    @GetMapping
    public ResponseEntity<List<FlightRouteResource>> getAllFlightRoutes() {
        var getAllFlightRoutesQuery = new GetAllFlightRoutesQuery();
        var flightRoutes = flightRouteQueryService.handle(getAllFlightRoutesQuery);
        var flightRoutesResources = flightRoutes.stream().map(FlightRouteResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(flightRoutesResources);
    }

//    @PutMapping("/{routeId}")
//    public ResponseEntity<FlightRouteResource> updateFlightRoute(@PathVariable Long routeId, @RequestBody UpdateFlightRouteResource resource) {
//        var command = UpdateFlightRouteCommandFromResourceAssembler.toCommandFromResource(routeId, resource);
//        var updated = flightRouteCommandService.handle(command);
//        if (updated.isEmpty()) return ResponseEntity.badRequest().build();
//        var resourceOut = FlightRouteResourceFromEntityAssembler.toResourceFromEntity(updated.get());
//        return ResponseEntity.ok(resourceOut);
//    }

    @DeleteMapping("/{routeId}")
    public ResponseEntity<?> deleteFlightRoute(@PathVariable Long routeId) {
        var command = new DeleteFlightRouteCommand(routeId);
        flightRouteCommandService.handle(command);
        return ResponseEntity.ok("Flight route successfully deleted.");
    }

    @PostMapping("/{routeId}/assign-drone")
    public ResponseEntity<FlightRouteResource> assignDroneToFlightRoute(
            @PathVariable Long routeId,
            @RequestBody AssignDroneToFlightRouteResource resource) {
        // Asegura que el ID de la ruta en el path y en el body coincidan (opcional)
        if (routeId != resource.flightRouteId()) {
            return ResponseEntity.badRequest().build();
        }
        var command = AssignDroneToFlightRouteResourceFromEntityAssembler.toCommandFromResource(resource);
        var updatedRoute = flightRouteCommandService.handle(command);
        if (updatedRoute.isEmpty()) return ResponseEntity.badRequest().build();
        var resourceOut = FlightRouteResourceFromEntityAssembler.toResourceFromEntity(updatedRoute.get());
        return ResponseEntity.ok(resourceOut);
    }

}
