package com.acme.center.platform.flight.application.internal.queryservices;

import com.acme.center.platform.flight.domain.model.aggregates.FlightRoute;
import com.acme.center.platform.flight.domain.model.queries.GetAllFlightRoutesQuery;
import com.acme.center.platform.flight.domain.model.queries.GetFlightRouteByIdQuery;
import com.acme.center.platform.flight.domain.model.valueobjects.FlightRouteStatus;
import com.acme.center.platform.flight.domain.services.FlightRouteQueryService;
import com.acme.center.platform.flight.infrastructure.persistence.jpa.repositories.FlightRouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightRouteQueryServiceImpl implements FlightRouteQueryService {

    private final FlightRouteRepository flightRouteRepository;

    public FlightRouteQueryServiceImpl(FlightRouteRepository flightRouteRepository) {
        this.flightRouteRepository = flightRouteRepository;
    }


    @Override
    public Optional<FlightRoute> handle(GetFlightRouteByIdQuery query){
        return flightRouteRepository.findById(query.flightRouteId());
    }

    @Override
    public List<FlightRoute> handle(GetAllFlightRoutesQuery query) {
        return flightRouteRepository.findAll();
    }

    @Override
    public List<FlightRoute> handleByStatus(FlightRouteStatus status) {
        return List.of();
    }

    @Override
    public List<FlightRoute> handleByDroneId(Long droneId) {
        return List.of();
    }


}
