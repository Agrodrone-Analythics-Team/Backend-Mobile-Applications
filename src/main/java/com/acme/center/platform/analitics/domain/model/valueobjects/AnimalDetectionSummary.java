package com.acme.center.platform.analitics.domain.model.valueobjects;

import java.util.Objects;

public record AnimalDetectionSummary(String species, int totalCount) {

    public AnimalDetectionSummary {
        if (species == null || species.isBlank()) {
            throw new IllegalArgumentException("Species cannot be null or blank");
        }
        if (totalCount < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
    }

    public String species() {
        return species;
    }

    public int totalCount() {
        return totalCount;
    }

    @Override
    public String toString() {
        return "%s: %d".formatted(species, totalCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AnimalDetectionSummary other)) return false;
        return species.equalsIgnoreCase(other.species) && totalCount == other.totalCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(species.toLowerCase(), totalCount);
    }
}