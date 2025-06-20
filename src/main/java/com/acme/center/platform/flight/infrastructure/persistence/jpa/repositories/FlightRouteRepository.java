package com.acme.center.platform.flight.infrastructure.persistence.jpa.repositories;

import com.acme.center.platform.flight.domain.model.aggregates.FlightRoute;
import com.acme.center.platform.flight.domain.model.valueobjects.FlightRouteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRouteRepository extends JpaRepository<FlightRoute, Long> {
    Optional<FlightRoute> findById(Long id);
}
