package com.promel.api.persistence.production.honeyproduction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface HoneyProductionRepository extends JpaRepository<HoneyProductionEntity, Long> {
    Optional<HoneyProductionEntity> findByAssociationIdAndEndDate(Long associationId, LocalDateTime endDate);
}
