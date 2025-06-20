package com.acme.center.platform.flight.application.internal.commandservices;

import com.acme.center.platform.flight.domain.model.aggregates.FlightRoute;
import com.acme.center.platform.flight.domain.model.commands.*;
import com.acme.center.platform.flight.domain.model.entities.Field;
import com.acme.center.platform.flight.domain.model.valueobjects.FlightLine;
import com.acme.center.platform.flight.domain.model.valueobjects.FlightParameters;
import com.acme.center.platform.flight.domain.services.FlightRouteCommandService;
import com.acme.center.platform.flight.infrastructure.persistence.jpa.repositories.DroneRepository;
import com.acme.center.platform.flight.infrastructure.persistence.jpa.repositories.FieldRepository;
import com.acme.center.platform.flight.infrastructure.persistence.jpa.repositories.FlightRouteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlightRouteCommandServiceImpl implements FlightRouteCommandService {

    private final FlightRouteRepository flightRouteRepository;
    //private final FieldRepository fieldRepository;
    private final DroneRepository droneRepository;

    public FlightRouteCommandServiceImpl(
            FlightRouteRepository flightRouteRepository,
            FieldRepository fieldRepository,
            DroneRepository droneRepository
    ) {
        this.flightRouteRepository = flightRouteRepository;
        //this.fieldRepository = fieldRepository;
        this.droneRepository = droneRepository;
    }

    @Override
    public Long handle(CreateFlightRouteCommand command) {
        // Validar existencia del campo
        //Field field = fieldRepository.findByExternalId(command.fieldExternalId())
        //        .orElseThrow(() -> new IllegalArgumentException("Field does not exist"));

        var parameters = new FlightParameters(command.altitude(), command.speed());

        FlightRoute route;

        if (command.flightLine() != null) {
            FlightLine line = command.flightLine();
            route = new FlightRoute(line, parameters);
        } else {
            throw new IllegalArgumentException("Either flight line or area polygon must be provided.");
        }

        try {
            flightRouteRepository.save(route);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving flight route: " + e.getMessage());
        }

        return route.getId();
    }

    @Override
    public Optional<FlightRoute> handle(AssignDroneToFlightRouteCommand command) {
        var route = flightRouteRepository.findById(command.flightRouteId())
                .orElseThrow(() -> new IllegalArgumentException("Flight route not found"));

        var drone = droneRepository.findById(command.droneId())
                .orElseThrow(() -> new IllegalArgumentException("Drone not found"));

        if (!route.canAssignDrone(drone)) {
            throw new IllegalArgumentException("Drone is not ready or route is not PLANNED.");
        }

        try {
            route.assignDrone(drone);
            return Optional.of(flightRouteRepository.save(route));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while assigning drone: " + e.getMessage());
        }
    }

    @Override
    public Optional<FlightRoute> handle(StartFlightRouteCommand command) {
        var route = flightRouteRepository.findById(command.flightRouteId())
                .orElseThrow(() -> new IllegalArgumentException("Flight route not found"));

        if (!route.canStartFlight()) {
            throw new IllegalArgumentException("Cannot start flight unless route is ASSIGNED.");
        }

        try {
            route.startFlight();
            return Optional.of(flightRouteRepository.save(route));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while starting flight: " + e.getMessage());
        }
    }

    @Override
    public Optional<FlightRoute> handle(CompleteFlightRouteCommand command) {
        var route = flightRouteRepository.findById(command.flightRouteId())
                .orElseThrow(() -> new IllegalArgumentException("Flight route not found"));

        if (!route.canCompleteFlight()) {
            throw new IllegalArgumentException("Cannot complete flight unless route is IN_PROGRESS.");
        }

        try {
            route.completeFlight();
            return Optional.of(flightRouteRepository.save(route));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while completing flight: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteFlightRouteCommand command) {
        if (!flightRouteRepository.existsById(command.flightRouteId())) {
            throw new IllegalArgumentException("Flight Route does not exist");
        }
        try {
            flightRouteRepository.deleteById(command.flightRouteId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting flight route: " + e.getMessage());
        }
    }
}