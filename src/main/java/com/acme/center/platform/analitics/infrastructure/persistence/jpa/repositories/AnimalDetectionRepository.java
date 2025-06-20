package com.acme.center.platform.analitics.infrastructure.persistence.jpa.repositories;

import com.acme.center.platform.analitics.domain.model.entities.AnimalDetection;
import com.acme.center.platform.flight.domain.model.valueobjects.FlightRouteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalDetectionRepository extends JpaRepository<AnimalDetection, Long> {

    // Encuentra todas las detecciones por ID de ruta de vuelo
    List<AnimalDetection> findAllByFlightRouteId(FlightRouteId flightRouteId);

    // Puedes añadir más queries específicos si lo necesitas
}