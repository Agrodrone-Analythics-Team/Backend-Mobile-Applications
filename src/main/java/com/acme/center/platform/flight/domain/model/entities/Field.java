package com.acme.center.platform.flight.domain.model.entities;

import com.acme.center.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
public class Field extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long externalId;

    @NotNull
    private String name;

    @NotNull
    private double areaHectares;

    private String cropType;

    public Field() {}

    public Field(Long externalId, String name, double areaHectares, String cropType) {
        this.externalId = externalId;
        this.name = name;
        this.areaHectares = areaHectares;
        this.cropType = cropType;
    }
}
