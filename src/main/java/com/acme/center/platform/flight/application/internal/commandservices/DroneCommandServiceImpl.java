package com.acme.center.platform.flight.application.internal.commandservices;

import com.acme.center.platform.flight.domain.model.commands.CreateDroneCommand;
import com.acme.center.platform.flight.domain.model.commands.DeleteDroneCommand;
import com.acme.center.platform.flight.domain.model.entities.Drone;
import com.acme.center.platform.flight.domain.services.DroneCommandService;
import com.acme.center.platform.flight.infrastructure.persistence.jpa.repositories.DroneRepository;
import org.springframework.stereotype.Service;

@Service
public class DroneCommandServiceImpl implements DroneCommandService {

    private final DroneRepository droneRepository;

    public DroneCommandServiceImpl(
            DroneRepository droneRepository
    )
    {
        this.droneRepository = droneRepository;
    }


    @Override
    public Long handle(CreateDroneCommand command) {
        var drone = new Drone(command);
        try {
            droneRepository.save(drone);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving drone: " + e.getMessage());
        }
        return drone.getId();
    }

    @Override
    public void handle(DeleteDroneCommand command) {
        if (!droneRepository.existsById(command.droneId())) {
            throw new IllegalArgumentException("Drone does not exist");
        }
        try {
            droneRepository.deleteById(command.droneId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting drone: " + e.getMessage());
        }
    }
}
