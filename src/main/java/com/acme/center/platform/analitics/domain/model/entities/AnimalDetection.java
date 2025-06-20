package com.acme.center.platform.analitics.domain.model.entities;

import com.acme.center.platform.flight.domain.model.valueobjects.FlightRouteId;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class AnimalDetection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private FlightRouteId flightRouteId;

    //private String species;

    private int count;

    private LocalDateTime timestamp;

    public AnimalDetection(FlightRouteId flightRouteId, int count, LocalDateTime timestamp) {
        this.flightRouteId = flightRouteId;
        //this.species = species;
        this.count = count;
        this.timestamp = timestamp;
    }

    public AnimalDetection() {}
}
