package com.promel.api.usecase.production.honeyproduction;

import com.promel.api.domain.model.HoneyProduction;
import com.promel.api.usecase.exception.ResourceNotFoundException;
import com.promel.api.usecase.production.honeyproduction.adapter.HoneyProductionAdapter;

import javax.inject.Named;
import java.util.List;

@Named
public class HoneyProductionFinder {
    private final HoneyProductionAdapter honeyProductionAdapter;

    public HoneyProductionFinder(HoneyProductionAdapter honeyProductionAdapter) {
        this.honeyProductionAdapter = honeyProductionAdapter;
    }

    public HoneyProduction findHoneyProductionInProgress(Long associationId) {
        return honeyProductionAdapter.findByAssociationIdAndEndDate(associationId, null).orElseThrow(() -> new ResourceNotFoundException("Honey production in progress not found"));
    }

    public boolean existsHoneyProductionInProgress(Long associationId) {
        return honeyProductionAdapter.existsByAssociationIdAndEndDate(associationId, null);
    }

    public List<HoneyProduction> findAllHoneyProductionByAssociation(Long associationId) {
        return honeyProductionAdapter.findAllHoneyProductionByAssociationId(associationId);
    }
}
