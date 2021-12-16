package com.promel.api.usecase.production.honeyproduction.adapter;

import com.promel.api.domain.model.HoneyProduction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HoneyProductionAdapter {
    HoneyProduction save(HoneyProduction model);

    Optional<HoneyProduction> findByAssociationIdAndEndDate(Long associationId, LocalDateTime endDate);

    boolean existsByAssociationIdAndEndDate(Long associationId, LocalDateTime endDate);

    List<HoneyProduction> findAllHoneyProductionByAssociationId(Long associationId);
}
