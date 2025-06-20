package com.acme.center.platform.flight.infrastructure.persistence.jpa.repositories;

import com.acme.center.platform.flight.domain.model.entities.Drone;
import com.acme.center.platform.flight.domain.model.valueobjects.DroneStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    List<Drone> findAllByStatusAndBatteryLevelGreaterThan(DroneStatus status, double minBattery);

    boolean existsBySerialNumber(String serialNumber);


}
