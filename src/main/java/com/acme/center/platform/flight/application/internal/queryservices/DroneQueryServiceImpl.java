package com.acme.center.platform.flight.application.internal.queryservices;

import com.acme.center.platform.flight.domain.model.entities.Drone;
import com.acme.center.platform.flight.domain.model.queries.GetAllDronesQuery;
import com.acme.center.platform.flight.domain.model.queries.GetDroneByIdQuery;
import com.acme.center.platform.flight.domain.services.DroneQueryService;
import com.acme.center.platform.flight.infrastructure.persistence.jpa.repositories.DroneRepository;
import com.acme.center.platform.flight.infrastructure.persistence.jpa.repositories.FlightRouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DroneQueryServiceImpl implements DroneQueryService {
    private final DroneRepository droneRepository;

    public DroneQueryServiceImpl(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public Optional<Drone> handle(GetDroneByIdQuery query) {
        return droneRepository.findById(query.droneId());
    }

    @Override
    public List<Drone> handle(GetAllDronesQuery query) {
        return droneRepository.findAll();
    }
}
