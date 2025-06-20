package com.acme.center.platform.flight.infrastructure.persistence.jpa.repositories;

import com.acme.center.platform.flight.domain.model.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FieldRepository extends JpaRepository<Field, Long> {
    Optional<Field> findByExternalId(Long externalId);

    boolean existsByExternalId(Long externalId);
}
